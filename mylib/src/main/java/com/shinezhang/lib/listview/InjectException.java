package com.shinezhang.lib.listview;

/**
 * Created by ShineZhang on 2016/11/29.
 */

public class InjectException extends RuntimeException {

    public InjectException() {
    }

    public InjectException(String detailMessage) {
        super(detailMessage);
    }

    public InjectException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public InjectException(Throwable throwable) {
        super(throwable);
    }
}
