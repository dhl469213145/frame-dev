/**
 * Copyright (c) 2016-2019 湖南神雀 All rights reserved.
 * <p>
 * https://www.sqqmall.com
 */

package com.framework.pay.utils;

import com.framework.pay.config.WeChatPrePayParamsConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 微信支付工具类
 *
 * @author dinghl
 */
public class WeChatPayUtils {
    @Autowired
    private static WeChatPrePayParamsConfig weChatPrePayParamsConfig;

    /**
     * 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
     * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     *
     特别注意以下重要规则：
     ◆ 参数名ASCII码从小到大排序（字典序）；
     ◆ 如果参数的值为空不参与签名；
     ◆ 参数名区分大小写；
     ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
     ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
     第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
     ◆ key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置

     * @return
     */
    public static String generateSign(final String key, final SortedMap<Object, Object> parameters) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (ObjectUtil.isNotEmpty(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(key);
        String sign = DigestUtils.md5Hex(sb.toString().getBytes());
        return sign.toUpperCase();
    }

    /**
     * 【微信支付】 将请求参数转换为xml格式的string
     *
     * @param parameters 请求参数
     * @return
     */
    public static String getRequestXml(final SortedMap<Object, Object> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if ("sign".equalsIgnoreCase(k)) {
                continue;
            }
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<").append(k).append("><![CDATA[").append(v).append("]]></").append(k).append(">");
            } else {
                sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
            }
        }
        sb.append("<sign>").append(parameters.get("sign")).append("</sign>").append("</xml>");
        return sb.toString();
    }

    public static SortedMap<Object, Object> generatePrePayParams(Long customerId, String orderNo, int totalFee, String ip, String tradeType, String openid) {
        // sortMap存储，按a-z顺序存储是后续签名操作的必要环节
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();

        packageParams.put("appid", weChatPrePayParamsConfig.getAppid());
        packageParams.put("mch_id", weChatPrePayParamsConfig.getMch_id());
        packageParams.put("nonce_str", WeChatPayUtils.getRandomString(30));// 随机串
        packageParams.put("body", weChatPrePayParamsConfig.getBody());
        packageParams.put("out_trade_no", orderNo);// 商户订单号
        packageParams.put("total_fee", totalFee);// 微信支付金额单位为（分）
        packageParams.put("spbill_create_ip", ip);
        packageParams.put("notify_url", weChatPrePayParamsConfig.getNotify_url());// 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        packageParams.put("trade_type", tradeType);        // H5支付类型为MWEB
        packageParams.put("openid", openid);        // jsapi必须传入
        packageParams.put("attach", customerId.toString());
        Date currentDate = new Date();
        String startTime = DateUtils.formatByTimepattern(currentDate);
        String endTime = DateUtils.formatByTimepattern(DateUtils.addDateMinutes(currentDate, weChatPrePayParamsConfig.getExpireMinute()));
        packageParams.put("time_start", startTime);// 订单生成时间
        packageParams.put("time_expire", endTime);// 订单失效时间
//        packageParams.put("scene_info",String.format(weChatH5PrePayConfig.getScene_info(), wapUrl, "红杜鹃支付"));// 场景信息
        return packageParams;
    }

    public static SortedMap<Object, Object> generatePayParams(String prepayId, String tradeType) {
        // sortMap存储，按a-z顺序存储是后续签名操作的必要环节
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", weChatPrePayParamsConfig.getAppid());
        packageParams.put("timeStamp", DateUtils.formatByTimepattern(new Date()));  // 时间戳
        packageParams.put("nonce_str", WeChatPayUtils.getRandomString(30)); // 随机串
        packageParams.put("package", prepayId);                                     // 统一下单接口返回的prepay_id参数值
        packageParams.put("signType", tradeType);                                   // 签名类型
        return packageParams;
    }


    public static String success() {
        StringBuffer result = new StringBuffer();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<xml><return_code><![CDATA[SUCCESS]]></return_code>");
        result.append("<return_msg><![CDATA[OK]]></return_msg>");
        result.append("</xml>");
        return result.toString();
    }

    public static String fail(String errorMsg) {
        StringBuffer result = new StringBuffer();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        result.append("<xml><return_code><![CDATA[FAIL]]></return_code>");
        result.append(String.format("<return_msg><![CDATA[%s]]></return_msg>", errorMsg));
        result.append("</xml>");
        return result.toString();
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
