package com.shinezhang.lib.indicator;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ShineZhang on 2016/12/14.
 */

public class Utils {

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
