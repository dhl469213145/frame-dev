package com.framework.pay.wechat.template;

import com.framework.pay.common.AbstractPayCallback;
import com.framework.pay.wechat.utils.WeChatPayUtils;
import com.framework.pay.utils.XNode;
import com.framework.pay.utils.XPathParser;
import com.framework.pay.utils.XPathWrapper;
import com.framework.pay.wechat.pojo.PayCallbackVO;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractWechatPayCallback extends AbstractPayCallback {

    /**
     * 根据微信返回的状态码，校验支付是否成功
     * 成功条件: return_code="SUCCESS" && result_code="SUCCESS"
     * @param params
     * @return
     */
    public PayCallbackVO check(String params) {
        logger.info("WechatPayCallback check is begin....");

        // 1.参数为空校验
        if(StringUtils.isNotEmpty(params)) {
            logger.error("WechatPayCallback check error....[params is empty]");
            return new PayCallbackVO(false, "params is empty");
        }

        XPathParser xpath = new XPathParser(params);
        XPathWrapper wrap = new XPathWrapper(xpath);
        String returnCode = wrap.get("return_code");
        String resultCode = wrap.get("result_code");

        // 2.微信返回状态码是否为空
        if(!StringUtils.isNotEmpty(returnCode) || !StringUtils.isNotEmpty(resultCode)) {
            logger.error("WechatPayCallback check error....[result_code or return_code is null]:::::result_code={},return_code={}", resultCode, returnCode);
            return new PayCallbackVO(false, "return_code or result_code is null");
        }
        PayCallbackVO checkServiceParamsVo = checkServiceParams(wrap);
        if(checkServiceParamsVo.getResult() == false){
            return checkServiceParamsVo;
        }

        // 3.return_code返回值校验
        if(!returnCode.equals("SUCCESS")) {
            logger.error("WechatPayCallback check error....[return_code != SUCCESS]:::::return_code={}", returnCode);
            return new PayCallbackVO(false, returnCode, StringUtils.isNotEmpty(wrap.get("return_code"))? wrap.get("return_code") : "");
        }

        // 4.result_code业务值校验
        if(!resultCode.equals("SUCCESS")) {
            logger.error("WechatPayCallback check error....[result_code != SUCCESS]:::::result_code={}", resultCode);
            return new PayCallbackVO(false, resultCode, StringUtils.isNotEmpty(wrap.get("result_code"))? wrap.get("result_code") : "");
        }

        // 5.签名校验
        String callbackSign = wrap.get("sign");
        if(StringUtils.isEmpty(callbackSign)) {
            logger.error("WechatPayCallback check error....[sign is empty]:::::params={}", params);
            return new PayCallbackVO(false, "sign is empty");
        }
        List<XNode> nodes = xpath.evalNodes("//xml/*");
        SortedMap<Object, Object> parameters = new TreeMap();
        for (XNode node : nodes) {
            parameters.put(node.name(), node.body());
        }
        String generateSign = WeChatPayUtils.generateSign(weChatConfig.getKey(), parameters);
        if(!Objects.equals(callbackSign, generateSign)) {
            logger.error("WechatPayCallback check error....[sign error]:::::callbackSign={}, generateSign={}", callbackSign, generateSign);
            return new PayCallbackVO(false, "sign check error");
        }

        return new PayCallbackVO(true, "success");
    }

    // 模板模式，用户自己去实现相关回调业务
    public abstract PayCallbackVO doCallbackService(String params);

    // 封装返回结果
    public String generateResult(PayCallbackVO payCallbackVO){
        if(payCallbackVO.getResult() == true) {
            return WeChatPayUtils.success();
        } else {
            return WeChatPayUtils.fail(payCallbackVO.getResultMsg());
        }

    }

    /**
     * 业务参数校验
     * @param wrap
     * @return
     */
    private PayCallbackVO checkServiceParams(XPathWrapper wrap) {
        String callbackOrderNo = wrap.get("out_trade_no");
        String attach = wrap.get("attach");
        String totalFee = wrap.get("total_fee");
        String cashFee = wrap.get("cash_fee");
        String transactionId = wrap.get("transaction_id");

        if(StringUtils.isEmpty(callbackOrderNo)) {
            logger.error("WechatPayCallback check error....[out_trade_no is empty]");
            return new PayCallbackVO(false, "callback params out_trade_no is empty");
        }
        if(StringUtils.isEmpty(attach)) {
            logger.error("WechatPayCallback check error....[attach is empty]");
            return new PayCallbackVO(false, "callback params attach is empty");
        }
        if(StringUtils.isEmpty(totalFee)) {
            logger.error("WechatPayCallback check error....[total_fee is empty]");
            return new PayCallbackVO(false, "callback params total_fee is empty");
        }
        if(StringUtils.isEmpty(cashFee)) {
            logger.error("WechatPayCallback check error....[cash_fee is empty]");
            return new PayCallbackVO(false, "callback params cash_fee is empty");
        }
        if(StringUtils.isEmpty(transactionId)) {
            logger.error("WechatPayCallback check error....[transaction_id is empty]");
            return new PayCallbackVO(false, "callback params transaction_id is empty");
        }
        return new PayCallbackVO(true, "success");
    }
}
