package com.framework.pay.wechat.controller;

import com.framework.pay.controller.AbstractController;
import com.framework.pay.wechat.template.AbstractWechatPayCallback;
import com.framework.pay.wechat.template.WechatPayCallbackTemplate;
import com.framework.pay.wechat.pojo.PayCallbackVO;
import com.framework.pay.wechat.utils.WeChatPayUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay/wechat/wechatPayCallback/")
@Api(value = "支付回调接口", tags = "{支付}")
public class WechatPayCallbackController extends AbstractController {

    @PostMapping("payCallback")
    public String payCallback(@RequestBody String returnParams) {
        logger.info("payCallback start..............");
        // 参数校验
        if(StringUtils.isEmpty(returnParams)) {
            logger.error("payCallback is error....[returnParams is empty]");
            return WeChatPayUtils.fail("returnParams is empty");
        }

        AbstractWechatPayCallback abstractWechatPayCallback = new WechatPayCallbackTemplate();
        PayCallbackVO payCallbackVO = abstractWechatPayCallback.doCallbackService(returnParams);
        logger.info("payCallback end..............");
        return payCallbackVO.getData().toString();
    }
}
