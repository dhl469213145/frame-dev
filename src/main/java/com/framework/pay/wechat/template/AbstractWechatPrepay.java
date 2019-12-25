package com.framework.pay.wechat.template;

import com.framework.pay.pojo.WeChatResultCode;
import com.framework.pay.utils.ObjectUtil;
import com.framework.pay.utils.XNode;
import com.framework.pay.utils.XPathParser;
import com.framework.pay.wechat.config.WeChatConfig;
import com.framework.pay.wechat.config.WeChatPrePayParamsConfig;
import com.framework.pay.wechat.config.WeChatUrlConfig;
import com.framework.pay.wechat.pojo.PrepayVo;
import com.mysql.jdbc.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Objects;

@Slf4j
public abstract class AbstractWechatPrepay {
    @Autowired
    private WeChatUrlConfig weChatUrlConfig;
    @Autowired
    private WeChatConfig weChatConfig;

    private final static String ENCODING = "UTF-8";


    public abstract PrepayVo prePay(String ipAddress, int orderFee, String orderNo, Long customerId, String openId, WeChatPrePayParamsConfig weChatPrePayParamsConfig);

    /**
     * 获取支付跳转地址
     *
     * @param requestXml
     * @param redirectUrl
     * @return  PrepayVo
     */

    public PrepayVo callUnified(final String requestXml, final String redirectUrl) {
        log.info("CallUnified method begin......");
        PrepayVo prepayVo = new PrepayVo();
        InputStream in = null;
        String resultXml = "";

        // 1.调用统一下单接口
        log.debug("CallUnified method running......[1.Call unified]");
        try {
            HttpClient client = HttpClients.custom().build();
            HttpPost httpost = new HttpPost(weChatUrlConfig.getUnifiedOrderUrl());
            httpost.setEntity(new StringEntity(requestXml, ENCODING));
            HttpResponse httpClientResponse = client.execute(httpost);
            resultXml = EntityUtils.toString(httpClientResponse.getEntity(), ENCODING);

            // 转换成xmlNode解析
            in = new ByteArrayInputStream(resultXml.getBytes(ENCODING));
            XPathParser xpath = new XPathParser(in);
            XNode returnCodeNode = xpath.evalNode("//return_code");
            XNode returnMsgNode = xpath.evalNode("//return_msg");
            boolean isSuccessOfCodeAndMsg = ObjectUtil.isNotEmpty(returnCodeNode) && ObjectUtil.isNotEmpty(returnMsgNode)
                    && Objects.equals("SUCCESS",returnCodeNode.body()) && Objects.equals("OK",returnMsgNode.body());

            // 失败返回
            if(!isSuccessOfCodeAndMsg) {
                WeChatResultCode weChatResultCode = WeChatResultCode.FAIL;
                if(Objects.nonNull(returnMsgNode)) {
                    weChatResultCode.msg = returnMsgNode.body();
                }

                prepayVo.setWeixinResultCode(weChatResultCode);
                log.debug("CallUnified method failed...... {}" + prepayVo.toString());
                return prepayVo;
            }

            // resultCode=fail时失败时返回
            XNode resultCodeNode = xpath.evalNode("//result_code");
            if(ObjectUtil.isNotEmpty(resultCodeNode) && Objects.equals("FAIL",resultCodeNode.body())){
                WeChatResultCode weixinResultCode = WeChatResultCode.FAIL;
                weixinResultCode.msg = xpath.evalNode("//err_code_des").body();
                prepayVo.setWeixinResultCode(weixinResultCode);
                log.debug("CallUnified method failed...... {}" + prepayVo.toString());
                return prepayVo;
            }

            // 返回结果都正确时保存prepayid、mweburl
            XNode prepayIdNode = xpath.evalNode("//prepay_id");
            String prePayId = "";
            String mwebUrl = "";
            if (ObjectUtil.isNotEmpty(prepayIdNode)) {
                prePayId = prepayIdNode.body();
            }
            XNode mwebUrlNode = xpath.evalNode("//mweb_url");
            if (ObjectUtil.isNotEmpty(mwebUrlNode)) {
                //加上应用前段回跳地址
                if(StringUtils.isNullOrEmpty(redirectUrl)){
                    mwebUrl = mwebUrlNode.body();
                }else{
                    mwebUrl = mwebUrlNode.body() + "&redirect_url=" + URLEncoder.encode(redirectUrl, ENCODING);
                }
            }
            prepayVo.setMwebUrl(mwebUrl);
            prepayVo.setPrepayId(prePayId);
        } catch (Exception e) {
            WeChatResultCode weixinResultCode = WeChatResultCode.FAIL;
            weixinResultCode.msg = e.getLocalizedMessage();
            prepayVo.setWeixinResultCode(weixinResultCode);
            log.debug("CallUnified method failed...... {}" + prepayVo.toString());
            return prepayVo;
        }finally {
            if(Objects.nonNull(in)){
                try {
                    in.close();
                } catch (IOException e) {
                    log.debug("CallUnified method failed...... {}" + prepayVo.toString());
                }
            }
        }
        log.debug("CallUnified method end...... {}" + prepayVo.toString());
        return prepayVo;
    }

}
