package com.framework.pay.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayResultCode implements IWeChatResultCode {

    SUCCESS("SUCCESS","OK"),
    FAIL("FAIL","");
    /**
     * code编码
     */
    public String code;
    /**
     * 中文信息描述
     */
    public String msg;
}
