package com.framework.pay.wechat.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayCallbackVO<T> implements Serializable {

    private T data;

    /**
     *  结果是否成功
     */
    private Boolean result;

    /**
     *  结果信息
     */
    private String msg;

    /**
     *  微信回调返回状态值
     */
    private String returnCode;

    /**
     *  微信回调返回状态信息
     */
    private String returnMsg;

    /**
     *  微信回调返回业务值
     */
    private String resultCode;

    /**
     *  微信回调返回业务信息
     */
    private String resultMsg;

    public PayCallbackVO(){}

    public PayCallbackVO(Boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public PayCallbackVO(Boolean result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public PayCallbackVO(Boolean result, String returnCode, String returnMsg){
        this.result = result;
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

}
