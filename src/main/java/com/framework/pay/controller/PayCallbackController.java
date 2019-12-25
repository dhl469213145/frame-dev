package com.framework.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.pay.exception.BizException;
import com.framework.pay.pojo.PayCallbackDTO;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay/callback")
@Api(value = "支付回调接口", tags = "{支付}")
public class PayCallbackController extends AbstractController {
//    @Autowired
//    private PayCallbackAgent payCallbackAgent;

    @PostMapping("payCallback")
    public void payCallback(@RequestBody String resultParams) {
//        logger.info("payback start..............");
//        // 参数校验
//        if(StringUtils.isBlank(resultParams)) {
//            throw new BizException("callback resultParams is null");
//        }
//
//        PayCallbackDTO payCallbackDTO = JSONObject.parseObject(resultParams, PayCallbackDTO.class);
//
//        // 代理类初始化
//        payCallbackAgent.init(body);
//        // 开始代理
//        payCallbackAgent.start();

        logger.info("payback end..............");
    }
}
