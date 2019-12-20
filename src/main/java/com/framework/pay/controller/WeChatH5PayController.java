package com.framework.pay.controller;

import com.sqqmall.common.utils.IPUtils;
import com.sqqmall.common.utils.R;
import com.sqqmall.modules.league.entity.OrdersEntity;
import com.sqqmall.modules.league.service.OrdersService;
import com.sqqmall.modules.pay.pojo.PrepayDTO;
import com.sqqmall.modules.pay.pojo.PrepayVo;
import com.sqqmall.modules.pay.pojo.WeChatResultCode;
import com.sqqmall.modules.pay.service.WeChatH5PayService;
import com.sqqmall.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;


/**
 * @author dinghl
 * @date 2019-12-13 15:40:05
 */
@RestController
@RequestMapping("league/wxh5pay")
@Api(value = "微信h5支付",description = "wxh5pay",tags = {"wxh5pay"})
public class WeChatH5PayController extends AbstractController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private WeChatH5PayService weChatH5PayService;

    @PostMapping("/pre-pay")
    @ApiOperation(value = "发起h5预支付", response = R.class)
    public R h5PrePay(HttpServletRequest request, @RequestBody PrepayDTO prepayDTO) {
        logger.info("h5 prePay begin...." + prepayDTO.toString());
        //获取客户id
        Long customerId = getUserId(request);
        //获取订单信息
        logger.info("h5 prePay running....[1.Get Order]:::::orderNo=" + prepayDTO.getOrderNo());
        OrdersEntity orderEntity = ordersService.getByOrderNo(prepayDTO.getOrderNo());
        if (Objects.isNull(orderEntity)) {
            logger.error("h5 prePay error....Can not find the order::::::orderNo=" + prepayDTO.getOrderNo());
            return R.fail("未找到订单");
        }
        logger.info("h5 prePay running....[2.Check current Customer is consistent in the Order]:::::currentCustomer=" + customerId);
        if (!Objects.equals(orderEntity.getCustomerId(), customerId)) {
            logger.error("h5 prePay error....The order does not belong to the customer:::::::currentCustomerId={} && orderNo={}", customerId, prepayDTO.getOrderNo());
            return R.fail("非当前用户订单");
        }
        //将订单金额单位元转成分
        int orderFee = orderEntity.getMoney().multiply(new BigDecimal(100), MathContext.UNLIMITED).intValue();
        logger.info("h5 prePay running....[3.Call WX Unified Method]");
       /* // 微信响应页面
        String wapUrl = "";
        if(!StringUtils.isNullOrEmpty(prepayDTO.getWapUrl()) && orderEntity.getOrderStatus() == 1) {
            wapUrl = prepayDTO.getWapUrl() + "?orderId=" + orderEntity.getId();
        }*/
        PrepayVo prepayVo = weChatH5PayService.prePay(IPUtils.getIpAddr(request), orderFee, prepayDTO.getOrderNo(), customerId, prepayDTO.getWapUrl());
        if (Objects.equals(prepayVo.getWeixinResultCode(), WeChatResultCode.FAIL)) {
            logger.error("h5 prePay error....Call WX Unified Method return error:::::::prepayVo={}", prepayVo.toString());
            return R.fail(prepayVo.getWeixinResultCode().getMsg());
        }
        return R.success(prepayVo.getMwebUrl());
    }
}
