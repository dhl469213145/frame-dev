package com.framework.pay.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.framework.pay.common.JsonLongSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "预支付信息")
public class PrepayDTO {
    /**
     * 订单流水号
     */
    @ApiModelProperty(value = "订单流水号")
    @NotBlank(message = "订单流水号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "支付类型(1、微信h5支付;2、微信jsapi支付;3、微信app支付;11、支付宝)")
    @NotBlank(message = "支付类型不能为空")
    private Integer payType;

    @ApiModelProperty(value = "支付配置信息")
    private String payConfigParams;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long customerId;

    @NotNull
    private Integer orderFee;

    private Integer liveTime;

    private String returnUrl;

    private String callbackURI;

    @ApiModelProperty(value = "回跳地址")
//    @NotBlank(message = "h5回跳地址不能为空")
    private String wapUrl;

    private String ip;

    /**
     * openid
     */
    @ApiModelProperty(value = "openid")
    private String openid;
}
