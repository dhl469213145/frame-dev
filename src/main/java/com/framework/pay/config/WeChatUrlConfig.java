package com.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pay.wechat.pay-url")
@Data
public class WeChatUrlConfig extends WeChatConfig {
    /**
     * 统一下单地址
     */
    private String unifiedOrderUrl;

    /**
     * 统一下单地址(冗余，容灾)
     */
    private String unifiedOrderSpareUrl;

    /**
     * 静默授权地址，跳转页面会带有微信创建的code，有此code来获取openid
     * 注.code只能用于一次获取openid
     * 注.必须在微信内访问
     */
    private String  authorizeUrl;

    /**
     * 统一下单地址(冗余，容灾)
     */
    private String  accessTokenUrl;
}
