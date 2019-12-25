package com.framework.pay.controller;

import com.framework.pay.agent.PayAgent;
import com.framework.pay.utils.IPUtils;
import com.framework.pay.utils.ObjectUtil;
import com.framework.pay.utils.R;
import com.framework.pay.pojo.PrepayDTO;
import com.framework.pay.wechat.pojo.PrepayVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dinghl
 * @date 2019-12-13 15:40:05
 */

@RestController
@RequestMapping("pay/pay")
@Api(value = "预支付统一调用接口",description = "prepay",tags = {"prepay"})
public class PayController extends AbstractController {
    @Autowired
    private PayAgent payAgent;

    @PostMapping("/pre-pay")
    @ApiOperation(value = "预支付", response = R.class)
    public R prepay(HttpServletRequest request, @RequestBody PrepayDTO prepayDTO) {
        logger.info("prepay begin....");
        prepayDTO.setIp(IPUtils.getIpAddr(request));
        PrepayVo prepayVo = payAgent.doPrePay(prepayDTO);

        return ObjectUtil.isNotEmpty(prepayVo) ? R.data(prepayVo) : R.fail("");
    }
}
