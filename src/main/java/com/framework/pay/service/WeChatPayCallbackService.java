package com.framework.pay.service;

import com.sqqmall.common.utils.Constant;
import com.sqqmall.modules.league.entity.OrdersEntity;
import com.sqqmall.modules.league.entity.PayEntity;
import com.sqqmall.modules.league.service.OrdersService;
import com.sqqmall.modules.league.service.PayService;
import com.sqqmall.modules.league.utils.WxPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class WeChatPayCallbackService extends AbstractPayCallbackService{
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PayService payService;

    @Override
    public String afterCallbackExcuter(String orderNo, String cashFee, String transactionId, String cutomerId) {
        OrdersEntity orderEntity = ordersService.getByOrderNo(orderNo);
        if (Objects.isNull(orderEntity)) {
            log.info("支付订单失败，订单未创建{}", orderNo);
            return WxPayUtils.fail("order is uncreated");
        }
        if (Objects.equals(orderEntity.getEnable(), new Integer(0))) {
            log.info("支付订单失败，订单数据已经失效:{}", orderEntity);
            return WxPayUtils.fail("order is disabled from business");
        }
        //密等处理 已成功的订单不需要再处理
        if (Objects.equals(orderEntity.getOrderStatus(), Constant.ORDERS_HAVE_PAID)) {
            log.info("支付订单已成功的订单不需要再处理:{}", orderEntity);
            return WxPayUtils.success();
        }
        //实际是分 需要转换成数据库中元做单位的数据
        BigDecimal fee = new BigDecimal(cashFee);
        fee = fee.divide(new BigDecimal(100));

        //获取支付信息
        //比较支付金额与回调金额是否一致 允许有两分钱以内误差
        if (fee.subtract(orderEntity.getMoney()).abs().compareTo(new BigDecimal(0.02)) == -1) {
            Date current = new Date();
            PayEntity payEntity = new PayEntity();
            payEntity.setCreateTime(current);
            payEntity.setCustomerId(Long.valueOf(cutomerId));
            payEntity.setCallbackTime(current);
            payEntity.setPayStatus(Constant.ORDERS_HAVE_PAID);
            payEntity.setOrderNo(orderNo);
            payEntity.setRemarks("红盟卡支付订单");
            payEntity.setMoney(fee);
            payEntity.setTransactionId(transactionId);
            payEntity.setEnable(Constant.ENABLE_VALID);
            payService.savePayedFromCallback(payEntity);
            log.info("红盟卡支付订单成功:{}", payEntity);

            //返回成功
            return WxPayUtils.success();
        } else {
            log.info("支付订单失败 订单金额与实际支付金额不一致:{},{},{}", orderNo, orderEntity.getPayment(), fee);
            return WxPayUtils.fail("payed money is not eq order sign payment");
        }
    }
}
