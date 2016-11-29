package com.shinezhang.lib.listview;

import android.support.v4.util.LruCache;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShineZhang on 2016/11/29.
 * <br/> may be use butter knife will be better
 */

public class AbstractListViewHolder {

    private static final String TAG = "AbstractListViewHolder";

    private static final LruCache<Class, List<Pair<Field, Integer>>> CLASS_FIELD_CACHE = new LruCache<Class, List<Pair<Field, Integer>>>(20);

    public AbstractListViewHolder(View view) {

        if (view == null) {
            Log.e(TAG, "view is null, ignore inject");
            return;
        }

        Class<? extends AbstractListViewHolder> clazz = getClass();
        List<Pair<Field, Integer>> list = CLASS_FIELD_CACHE.get(getClass());
        if (list == null) {
            list = getClassViewField(clazz);
            if (list == null || list.size() == 0) {
                Log.w(TAG, "no field with annotation " + InjectViewHolder.class.getName()
                        + " found at class " + clazz.getName());
                CLASS_FIELD_CACHE.put(clazz, new ArrayList<Pair<Field, Integer>>());
                return;
            }

            CLASS_FIELD_CACHE.put(clazz, list);
        }

        try {
            injectView(clazz, list, view);
        } catch (Exception e) {
            throw new InjectException(e);
        }
    }

    private List<Pair<Field, Integer>> getClassViewField(Class<? extends AbstractListViewHolder> clazz) {
        List<Pair<Field, Integer>> listViewField = new ArrayList<Pair<Field, Integer>>();

        Class<?> cls = clazz;
        List<Class<?>> listClass = new ArrayList<Class<?>>();
        while (cls != AbstractListViewHolder.class) {
            listClass.add(cls);
            cls = cls.getSuperclass();
        }

        Field[] fields;
        for (int i = listClass.size() - 1; i >= 0; i--) {
            cls = listClass.get(i);
            fields = cls.getDeclaredFields();

            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                if (!field.isAnnotationPresent(InjectViewHolder.class)) {
                    continue;
                }

                if (!View.class.isAssignableFrom(field.getType())) {
                    Log.w(TAG, "the field " + field.getName() + "(type is " + field.getType().getName()
                            + ") at class " + clazz.getName()
                            + " is not inherited from " + View.class.getName());
                    continue;
                }

                InjectViewHolder injectViewHolder = field.getAnnotation(InjectViewHolder.class);
                if (injectViewHolder == null) {
                    Log.w(TAG, "the field " + field.getName() + "(type is " + field.getType().getName()
                            + ") at class " + clazz.getName()
                            + " with annotation InjectViewHolder null");
                    continue;
                }

                Pair<Field, Integer> pair = Pair.create(field, Integer.valueOf(injectViewHolder.value()));
                listViewField.add(pair);
            }
        }

        return listViewField;
    }

    private void injectView(Class<? extends AbstractListViewHolder> clazz, List<Pair<Field, Integer>> list, View view) throws Exception {
        int injectViewId;
        View injectView;
        Field field;
        for (Pair<Field, Integer> pair : list) {
            field = pair.first;
            injectViewId = pair.second.intValue();
            injectView = view.findViewById(pair.second.intValue());

            if (injectView == null) {
                String resIdName;
                try {
                    resIdName = view.getResources().getResourceName(injectViewId);
                } catch (Exception e) {
                    resIdName = String.valueOf(injectViewId);
                }

                String errMsg = "the field " + field.getName() + " at class " + clazz.getName()
                        + " inject failed as the view with id " + resIdName + " can not find";
                throw new RuntimeException(errMsg);
            }

            field.set(this, injectView);
        }
    }
}
