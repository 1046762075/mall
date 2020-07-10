package com.sso.core.exception;

/**
 * <p>Title: SsoException</p>
 * Description：
 * date：2020/6/27 13:42
 */
public class SsoException extends RuntimeException {

    private static final long serialVersionUID = 42L;

    public SsoException(String msg) {
        super(msg);
    }

    public SsoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SsoException(Throwable cause) {
        super(cause);
    }

}
