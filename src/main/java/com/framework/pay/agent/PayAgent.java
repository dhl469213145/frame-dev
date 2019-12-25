package com.framework.pay.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.pay.common.AbstractPrePay;
import com.framework.pay.exception.BizException;
import com.framework.pay.pojo.PrepayDTO;
import com.framework.pay.utils.PayTypeConstant;
import com.framework.pay.wechat.config.WeChatPrePayParamsConfig;
import com.framework.pay.wechat.pojo.PayCallbackDTO;
import com.framework.pay.wechat.pojo.PrepayVo;
import com.framework.pay.wechat.template.AbstractWechatPrepay;
import com.framework.pay.wechat.template.WeChatH5PayService;
import com.framework.pay.wechat.template.WeChatJsapiPayService;
import org.apache.commons.lang.StringUtils;

public class PayAgent {


    public void init(String params) {


    }

    public PrepayVo doPrePay(PrepayDTO prepayDTO) {
        if(prepayDTO.getPayType() == PayTypeConstant.PAY_TYPE_WECHAT_H5.getType()) {
            AbstractWechatPrepay abstractPrePay = new WeChatH5PayService();
            WeChatPrePayParamsConfig weChatPrePayParamsConfig = null;
            if(StringUtils.isNotEmpty(prepayDTO.getPayConfigParams())) {
                weChatPrePayParamsConfig = JSON.parseObject(prepayDTO.getPayConfigParams(), WeChatPrePayParamsConfig.class);

            }
            return abstractPrePay.prePay(prepayDTO.getIp(), prepayDTO.getOrderFee(), prepayDTO.getOrderNo(), prepayDTO.getCustomerId(),
                    prepayDTO.getWapUrl(), weChatPrePayParamsConfig);
        } else if(prepayDTO.getPayType() == PayTypeConstant.PAY_TYPE_WECHAT_JSAPI.getType()) {
            AbstractWechatPrepay abstractPrePay = new WeChatJsapiPayService();
            WeChatPrePayParamsConfig weChatPrePayParamsConfig = null;
            if(StringUtils.isNotEmpty(prepayDTO.getPayConfigParams())) {
                weChatPrePayParamsConfig = JSON.parseObject(prepayDTO.getPayConfigParams(), WeChatPrePayParamsConfig.class);
            }
            return abstractPrePay.prePay(prepayDTO.getIp(), prepayDTO.getOrderFee(), prepayDTO.getOrderNo(), prepayDTO.getCustomerId(),
                    prepayDTO.getWapUrl(), weChatPrePayParamsConfig);
        } else if(prepayDTO.getPayType() == PayTypeConstant.PAY_TYPE_WECHAT_APP.getType()) {
            //TODO
        } else if(prepayDTO.getPayType() == PayTypeConstant.PAY_TYPE_WECHAT_NATIVE.getType()) {
            //TODO
        }

        return null;

    }
}
