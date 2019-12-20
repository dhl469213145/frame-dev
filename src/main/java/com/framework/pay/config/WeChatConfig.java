package com.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pay.wechat")
@Component
@Data
public class WeChatConfig {
    /**
     * 公众账号ID
     */
    private String appid;

    /**
     * 公众账号ID
     */
    private String key;

    /**
     * 公众账号ID
     */
    private String mch_id;

    /**
     * 公众账号ID
     */
    private String apisecret;


}
