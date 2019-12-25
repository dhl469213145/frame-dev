package com.framework.pay.wechat;


import com.framework.pay.common.AbstractPay;

public abstract class AbstractWechatPay extends AbstractPay {
    abstract void prePay();

    abstract void callBack();
}
