package com.liouxb.bi.exception;


/**
 * @author liouwb
 */
public class RequestParamsValidException extends Exception {


    public static final long serialVersionUID = 1L;

    private String msg;

    public RequestParamsValidException(String msg) {
        super(msg);
    }


}
