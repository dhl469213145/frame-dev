package com.framework.pay.wechat.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "预支付信息")
public class PrepayDTO {
    /**
     * 订单流水号
     */
    @ApiModelProperty(value = "订单流水号")
    @NotBlank(message = "订单流水号不能为空")
    private String orderNo;


    /**
     * h5回跳地址
     */
    @ApiModelProperty(value = "h5回跳地址")
    @NotBlank(message = "h5回跳地址不能为空")
    private String wapUrl;

    /**
     * openid
     */
    @ApiModelProperty(value = "openid")
    private String openid;
}
