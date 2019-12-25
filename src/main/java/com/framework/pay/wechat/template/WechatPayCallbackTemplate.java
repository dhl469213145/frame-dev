package com.framework.pay.wechat.template;

import com.alibaba.fastjson.JSON;
import com.framework.pay.utils.XPathParser;
import com.framework.pay.utils.XPathWrapper;
import com.framework.pay.wechat.pojo.PayCallbackDTO;
import com.framework.pay.wechat.pojo.PayCallbackVO;
import com.framework.pay.wechat.service.IWechatPayCallbackService;
import org.springframework.beans.factory.annotation.Autowired;

public class WechatPayCallbackTemplate extends AbstractWechatPayCallback {
    @Autowired
    private IWechatPayCallbackService callbackService;


    // 模板模式，用户自己去实现相关回调业务
    @Override
    public PayCallbackVO doCallbackService(String params){
        logger.info("doCallbackService is begin.....");

        // 1、微信返回结果校验
        logger.info("doCallbackService is running.....[1.check params]");
        PayCallbackVO payCallbackVO = check(params);
        if(false == payCallbackVO.getResult()) {
            return payCallbackVO;
        }

        // 2、回调业务实现
        logger.info("doCallbackService is running.....[2.do service]");
        XPathParser xpath = new XPathParser(params);
        XPathWrapper wrap = new XPathWrapper(xpath);
        String attach = wrap.get("attach");// 业务端传来的参数，json，返回给业务端进行逻辑处理
        PayCallbackDTO payCallbackDTO = JSON.parseObject(attach, PayCallbackDTO.class);
        Boolean isDoServiceOk = callbackService.doCallbackService(payCallbackDTO);
        if(!isDoServiceOk)  {
            logger.error("doCallbackService is error.....[do service error]:::::payCallbackDTO={}", payCallbackDTO.toString());
            return new PayCallbackVO(false, "payCallback do service find error");
        }

        // 3、封装回调响应参数
        logger.info("doCallbackService is running.....[3.generate result]");
        String resultParam = generateResult(payCallbackVO);

        logger.info("doCallbackService is finish.");
        return new PayCallbackVO(true, "success", resultParam);
    }
}
