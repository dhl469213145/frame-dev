package com.framework.pay.wechat.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.framework.pay.common.JsonLongSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "支付回调对象")
public class PayCallbackDTO {
    private String orderNo;

    private Integer payType;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long customerId;

    private BigDecimal orderFee;

    private Integer liveTime;

    private String returnUrl;

    private String callbackService;


    public static void main(String[] args) {
        PayCallbackDTO payCallbackDTO = new PayCallbackDTO();
        System.out.println(payCallbackDTO.toString());
    }
}
