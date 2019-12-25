package com.framework.pay.utils;

public enum PayTypeConstant {

    PAY_TYPE_WECHAT_H5(1,"h5"),
    PAY_TYPE_WECHAT_JSAPI(2,"jsapi"),
    PAY_TYPE_WECHAT_APP(3,"app"),
    PAY_TYPE_WECHAT_NATIVE(4,"native");



    private String typeName;
    int type;

    private PayTypeConstant(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public int getType() {
        return this.type;
    }




}
