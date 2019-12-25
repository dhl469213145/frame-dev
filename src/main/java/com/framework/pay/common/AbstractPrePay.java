package com.framework.pay.common;

import com.framework.pay.wechat.config.WeChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractPrePay {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
