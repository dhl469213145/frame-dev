package com.framework.pay.wechat.pojo;

import com.framework.pay.pojo.WeChatResultCode;
import lombok.Data;

/**
 * 发送微信预支付返回结果对象
 */
@Data
public class PrepayVo {
    static final long serialVersionUID = 1L;

    private String prepayId;

    private String mwebUrl;

    private WeChatResultCode weixinResultCode;
}