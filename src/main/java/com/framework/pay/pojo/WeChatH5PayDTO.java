package com.framework.pay.pojo;

public class WeChatH5PayDTO {
    //h5支付appid
    private String appid;
    //公众号支付appid
    //private String wxAppid;
    //商户号
    private String mchId;
    //商户秘钥
    private String key;
    //H5支付的交易类型为MWEB
    //签名类型
    private String signType;
    //通知地址
    private String notifyUrl;
    //场景信息-WAP网站应用
    private String sceneInfo;
    //超时时间 5分钟
    private int timeoutExpress;
//    @Autowired
//    private ObjectMapper om;
}
