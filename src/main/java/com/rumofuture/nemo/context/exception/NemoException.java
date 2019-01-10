package com.rumofuture.nemo.context.exception;

/**
 * Nemo业务异常类
 *
 * @author 王振琦
 * createAt: 2018/08/01
 * updateAt: 2018/08/01
 */
public class NemoException extends RuntimeException {
    private int statusCode;

    public NemoException(int statusCode) {
        this.statusCode = statusCode;
    }

    public NemoException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
