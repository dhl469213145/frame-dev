package com.framework.pay.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "支付回调对象")
public class PayCallbackDTO {
    private String orderNo;

    private Integer payType;

    @JsonDeserialize
    private Long customerId;

    private BigDecimal orderFee;

    private Integer liveTime;

    private String returnUrl;

    private String callbackService;

}
