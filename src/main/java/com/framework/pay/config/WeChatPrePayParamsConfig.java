package com.framework.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sqqmall.pay.wechat.pre-pay-params")
@Data
public class WeChatPrePayParamsConfig extends WeChatConfig {
    /**
     * 公众账号ID (必填)
     * 微信支付分配的公众账号ID（企业号corpid即为此appId
     * eg.wxd678efh567hg6787
     * */
    private String appid;

    /**
     * 商户号 (必填)
     * 微信支付分配的商户号
     * eg.1230000109
     * */
    private String mch_id;

    /**
     * 设备号
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     * eg.013467007045764
     * */
    private String device_info;

    /**
     * 随机字符串 (必填)
     * 随机字符串，长度要求在32位以内。推荐随机数生成算法
     * eg.5K8264ILTKCH16CQ2502SI8ZNMTM67VS
     * */
    private String nonce_str;

    /**
     * 签名 (必填)
     * 通过签名算法计算得出的签名值
     * eg.C380BEC2BFD727A4B6845133519F3AD6
     * */
    private String sign;

    /**
     * 签名类型
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     * */
    private String sign_type;

    /**
     * 商品描述 (必填)
     * 商品简单描述
     * eg.腾讯充值中心-QQ会员充值
     * */
    private String body;

    /**
     * 商品详情
     * 商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，
     * */
    private String detail;

    /**
     * 附加数据
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * eg.深圳分店
     * */
    private String attach;

    /**
     * 商户订单号 (必填)
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。
     * eg.20150806125346
     * */
    private String out_trade_no;

    /**
     * 标价币种
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * */
    private String fee_type;

    /**
     * 标价金额 (必填)
     * 订单总金额，单位为分
     * */
    private Integer total_fee;

    /**
     * 终端IP (必填)
     * 支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
     * */
    private String spbill_create_ip;

    /**
     * 交易起始时间
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
     * 备注.这里由调用方法处自行生成，不从配置文件读取
     * */
    private String time_start;

    /**
     * 交易结束时间(这里传入分钟，与当前时间计算后得出结束时间)
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
     * 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     * time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单
     * 建议：最短失效时间间隔大于1分钟
     * */
    private Integer expireMinute;

    /**
     * 订单优惠标记
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
     * eg.WXG
     * */
    private String goods_tag;

    /**
     * 通知地址(必填)
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     * */
    private String notify_url;

    /**
     * 交易类型 (必填)
     * JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付
     * 创建了所有类型的属性，对应预支付接口的trade_type，调用者在相应接口处自行选择对应的交易类型。
     *
     * */
    private String trade_type_native;
    private String trade_type_app;
    private String trade_type_h5;
    private String trade_type_jsapi;

    /**
     * 商品ID
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     * 12235413214070356458058
     * */
    private String product_id;

    /**
     * 指定支付方式
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     * */
    private String limit_pay;

    /**
     * 用户标识
     * trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
     * eg.oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * */
    private String openid;

    /**
     * 电子发票入口开放标识
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     * eg.Y
     * */
    private String receipt;

    /**
     * 场景信息
     * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。该字段为JSON对象数据
     * eg.{"store_info" : {
        "id": "SZTX001",
        "name": "腾大餐厅",
        "area_code": "440305",
        "address": "科技园中一路腾讯大厦" }}
     * */
    private String scene_info;

}
