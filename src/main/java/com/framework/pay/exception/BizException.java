/**
 * Copyright (c) 2016-2019 湖南神雀 All rights reserved.
 * <p>
 * https://www.sqqmall.com
 */

package com.framework.pay.exception;


import com.framework.pay.utils.ServiceCode;

/**
 * 自定义异常
 *
 * @author Mark WenJunChi
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public BizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BizException(ServiceCode serviceCode) {
        super(serviceCode.getMsg());
        this.msg = serviceCode.getMsg();
        this.code = serviceCode.getCode();
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
