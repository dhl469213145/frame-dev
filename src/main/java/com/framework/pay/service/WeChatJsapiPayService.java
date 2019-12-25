/*
package com.framework.pay.service;

import com.alibaba.fastjson.JSON;
import com.sqqmall.modules.pay.config.WeChatPrePayParamsConfig;
import com.sqqmall.modules.pay.config.WeChatUrlConfig;
import com.sqqmall.modules.pay.pojo.PrepayVo;
import com.sqqmall.modules.pay.utils.WeChatPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.SortedMap;

*/
/**
 *
 *
 * @author dinghl
 * @date 2019-12-13 16:06:05
 *//*

@Slf4j
@Service("weChatJsapiPayService")
public class WeChatJsapiPayService extends AbstractWechatPayService {
//    @Autowired
//    private WeChatPrePayParamsConfig weChatPrePayParamsConfig;
//    @Autowired
//    private WeChatUrlConfig weChatUrlConfig;

    private final static String ENCODING = "UTF-8";

   */
/* public String getAuthorizeUri(String redirectUri) {
        try {
            redirectUri = URLEncoder.encode(redirectUri, ENCODING);
        } catch (UnsupportedEncodingException e) {
         log.error("获取jsapi静默授权地址失败,{}",e);
        }
        return String.format(weChatUrlConfig.getAuthorizeUrl() + "?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                weChatPrePayParamsConfig.getAppid(), redirectUri, "myState");
    }

    public String getOpenidByCode(String code) {
        HttpClient client = HttpClients.custom().build();
        String getAccessToken = String.format(weChatUrlConfig.getAccessTokenUrl() + "?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                weChatPrePayParamsConfig.getAppid(), weChatPrePayParamsConfig.getKey(), code);
        HttpGet httpGet = new HttpGet(getAccessToken);
        try {
            HttpResponse httpClientResponse = client.execute(httpGet);
            String getAccessTokenResult = EntityUtils.toString(httpClientResponse.getEntity(), ENCODING);
            log.info("前段获取到code之后，传递到后端换取openid,{}",getAccessTokenResult);
            return getAccessTokenResult;
        }catch (Exception e){
            log.error("前段获取到code之后，传递到后端换取openid,{}" + e.getMessage(), e);
            return null;
        }
    }

    *//*
*/
/**
     * 生成并发送微信支付产生预支付信息 jsapi支付
     *
     * @return String
     *//*
*/
/*
    public PrepayVo prePay(String ipAddress, int orderFee, String orderNo, Long customerId, String openId){
        log.info("jsapiPrePay  begin......");

        // 1.组装预支付参数
        log.debug("jsapiPrePay  running......[1.Generate Params]");
        SortedMap<Object, Object> packageParams = WeChatPayUtils.generatePrePayParams(customerId, orderNo, orderFee, ipAddress, weChatPrePayParamsConfig.getTrade_type_jsapi(), openId);

        // 2.对参数签名
        log.debug("jsapiPrePay  running......[2.Generate sign by params]:::::params=" + packageParams.toString());
        String sign = WeChatPayUtils.generateSign(weChatPrePayParamsConfig.getKey(), packageParams);
        packageParams.put("sign", sign);

        // 3.将参数转换成xml
        log.debug("jsapiPrePay  running......[3.Generate requestXML]:::::sign=" + sign);
        String requestXml = WeChatPayUtils.getRequestXml(packageParams);

        // 4.调用统一下单接口
        log.debug("jsapiPrePay  running......[4.Call unified method]:::::requestXML=" + requestXml);
        PrepayVo prepayVo = callUnified(requestXml, "");

        log.debug("jsapiPrePay  end......prepayVo=" + prepayVo.toString());
        return prepayVo;
    }

    *//*
*/
/**
     * 生成吊起jsapi支付所需要的参数
     *
     * @return json
     *//*
*/
/*
    public String generatePayParams(String prepayId){
        log.info("generatePayParams  begin......");

        // 1.组装调用参数，按字母顺序排序
        log.debug("jsapiPrePay  running......[1.Generate Params]:::::prepayId={}", prepayId);
        SortedMap<Object, Object> packageParams = WeChatPayUtils.generatePayParams(prepayId, weChatPrePayParamsConfig.getTrade_type_jsapi());

        // 2.对参数签名
        log.debug("generatePayParams  running......[2.Generate sign by params]:::::params={}", packageParams.toString());
        String sign = WeChatPayUtils.generateSign(weChatPrePayParamsConfig.getKey(), packageParams);
        packageParams.put("paySign", sign);

        log.debug("generatePayParams  end......");
        return JSON.toJSON(packageParams).toString();
    }*//*


}
*/
