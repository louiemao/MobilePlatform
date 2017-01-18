package com.sws.platform.mobile.common.exception;

/**
 * 业务异常
 * Created by MaoLiang on 2016/4/20.
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
