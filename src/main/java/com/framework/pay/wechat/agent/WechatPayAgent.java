package com.framework.pay.wechat.agent;

import com.alibaba.fastjson.JSONObject;
import com.framework.pay.exception.BizException;
import com.framework.pay.wechat.pojo.PayCallbackDTO;
import org.apache.commons.lang.StringUtils;

public class WechatPayAgent {


    public void init(String params) {
        if(StringUtils.isBlank(params)) {
            throw new BizException("callback params is null");
        }
        PayCallbackDTO payCallbackDTO = JSONObject.parseObject(params, PayCallbackDTO.class);

    }

    public void start() {
        // 如果是微信
            // h5支付
            // jsapi支付

        // 支付宝支付

        // 其它
    }
}
