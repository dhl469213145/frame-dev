package com.framework.pay.wechat.template;
import com.framework.pay.wechat.config.WeChatPrePayParamsConfig;
import com.framework.pay.wechat.pojo.PrepayVo;
import com.framework.pay.wechat.utils.WeChatPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.SortedMap;


/**
 *
 *
 * @author dinghl
 * @date 2019-12-13 16:06:05
 */

@Slf4j
@Service("weChatH5PayService")
public class WeChatH5PayService extends AbstractWechatPrepay {
    @Autowired
    private WeChatPrePayParamsConfig weChatPrePayParamsConfig;

    /**
     * 生成并发送微信支付产生预支付信息 h5支付
     *
     * @return String
     */

    public PrepayVo prePay(String ipAddress, int orderFee, String orderNo, Long customerId, String redirectUrl){
        log.info("generateAndSendH5PrePay  begin......");

        // 1.组装预支付参数
        log.debug("generateAndSendH5PrePay  running......[1.Generate Params]");
        SortedMap<Object, Object> packageParams = WeChatPayUtils.generatePrePayParams(customerId, orderNo, orderFee, ipAddress, weChatPrePayParamsConfig.getTrade_type_h5(), "");

        // 2.对参数签名
        log.debug("generateAndSendH5PrePay  running......[2.Generate sign by params]:::::params=" + packageParams.toString());
        String sign = WeChatPayUtils.generateSign(weChatPrePayParamsConfig.getKey(), packageParams);
        packageParams.put("sign", sign);

        // 3.将参数转换成xml
        log.debug("generateAndSendH5PrePay  running......[3.Generate requestXML]:::::sign=" + sign);
        String requestXml = WeChatPayUtils.getRequestXml(packageParams);

        // 4.调用统一下单接口
        log.debug("generateAndSendH5PrePay  running......[4.Call unified method]:::::requestXML=" + requestXml);
        PrepayVo prepayVo = callUnified(requestXml, redirectUrl);

        log.debug("generateAndSendH5PrePay  end......prepayVo=" + prepayVo.toString());
        return prepayVo;
    }
}

