/*
package com.framework.pay.controller;

import com.qiniu.util.StringUtils;
import com.sqqmall.common.utils.*;
import com.sqqmall.modules.league.entity.OrdersEntity;
import com.sqqmall.modules.league.service.OrdersService;
import com.sqqmall.modules.pay.pojo.PrepayDTO;
import com.sqqmall.modules.pay.pojo.PrepayVo;
import com.sqqmall.modules.pay.pojo.WeChatResultCode;
import com.sqqmall.modules.pay.service.WeChatJsapiPayService;
import com.sqqmall.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;


*/
/**
 * @author dinghl
 * @date 2019-12-13 15:40:05
 *//*

@RestController
@RequestMapping("league/jsapipay")
@Api(value = "微信jsapi支付",description = "jsapipay",tags = {"jsapipay"})
public class WeChatJsapiPayController extends AbstractController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private WeChatJsapiPayService weChatJsapiPayService;

    @GetMapping("/getAuthorizeUrl")
    @ApiOperation(value = "获取静默授权接口地址", response = R.class)
    public R getAuthorizeUrl(@ApiParam(name = "redirectUri", value = "回跳地址", required = true) @RequestParam String redirectUri) {
        logger.info("GetAuthorizeUrl start.......");
        if(StringUtils.isNullOrEmpty(redirectUri)) {
            return R.fail(ServiceCode.DB_PARAM_NULL);
        }
        String authorizeUrl = weChatJsapiPayService.getAuthorizeUri(redirectUri);
        logger.debug("GetAuthorizeUrl end.......::::authorizeUrl={}", authorizeUrl);
        return StringUtils.isNullOrEmpty(authorizeUrl) ? R.fail("静默授权地址为空") : R.data(ResultCode.SUCCESS.getCode(), authorizeUrl, ServiceCode.DB_RETURN.getMsg());
    }

    @GetMapping("/getOpenidByCode")
    @ApiOperation(value = "获取OpenId", response = R.class)
    public R getOpenidByCode(@ApiParam(name = "code", value = "code", required = true) @RequestParam String code) {
        logger.info("GetOpenidByCode start");
        if(StringUtils.isNullOrEmpty(code)) {
            return R.fail(ServiceCode.DB_PARAM_NULL);
        }
        String openId = weChatJsapiPayService.getOpenidByCode(code);
        logger.debug("GetOpenidByCode end.......::::openId={}", openId);
        return StringUtils.isNullOrEmpty(openId) ? R.fail("openid为空") : R.data(ResultCode.SUCCESS.getCode(), openId, ServiceCode.DB_RETURN.getMsg());
    }

    @PostMapping("/pre-pay")
    @ApiOperation(value = "发起jsapi预支付", response = R.class)
    public R h5PrePay(HttpServletRequest request, HttpServletResponse response, @RequestBody PrepayDTO prepayDTO) {
        logger.info("jsapi prePay begin....");

        if(ObjectUtil.isEmpty(prepayDTO) || StringUtils.isNullOrEmpty(prepayDTO.getOpenid())) {
            logger.error("jsapi prePay error....Reaquest Params is null or openid is null::::::prepayDTO={}", prepayDTO.toString());
            return R.fail("请求对象为空，或openid为空");
        }

        // 1,获取订单信息
        logger.info("jsapi prePay running....[1.Get Order]:::::orderNo={}", prepayDTO.getOrderNo());
        OrdersEntity orderEntity = ordersService.getByOrderNo(prepayDTO.getOrderNo());
        if (Objects.isNull(orderEntity)) {
            logger.error("jsapi prePay error....Can not find the order::::::orderNo={}", prepayDTO.getOrderNo());
            return R.fail("未找到订单");
        }

        // 2,判断是否当前用户的订单
        //获取客户id
        Long customerId = getUserId(request);
        logger.debug("jsapi prePay running....[2.Check current Customer is consistent in the Order]:::::currentCustomer={}", customerId);
        if (!Objects.equals(orderEntity.getCustomerId(), customerId)) {
            logger.error("jsapi prePay error....The order does not belong to the customer:::::::currentCustomerId={} && orderNo={}", customerId, prepayDTO.getOrderNo());
            return R.fail("非当前用户订单");
        }

        // 3,组装参数，调用jsapi的预支付接口
        logger.debug("jsapi prePay running....[3.Call WX Unified Method]:::::openId={}", prepayDTO.getOpenid());
        // 将订单金额单位元转成分  ?是否要加上邮费
        int orderFee = orderEntity.getMoney().multiply(new BigDecimal(100), MathContext.UNLIMITED).intValue();
        PrepayVo prepayVo = weChatJsapiPayService.prePay(IPUtils.getIpAddr(request), orderFee, prepayDTO.getOrderNo(), customerId, prepayDTO.getOpenid());
        if (Objects.equals(prepayVo.getWeixinResultCode(), WeChatResultCode.FAIL)) {
            logger.error("jsapi prePay error....Call WX Unified Method return error:::::::prepayVo={}", prepayVo.toString());
            return R.fail(prepayVo.getWeixinResultCode().getMsg());
        }

        // 4,重新签名，提供给前端调用支付接口
        logger.debug("jsapi prePay running....[4.Generate PayParams]:::::prepayV={}", prepayVo.toString());
        String paramsJson = weChatJsapiPayService.generatePayParams(prepayVo.getPrepayId());

        logger.debug("jsapi prePay end....paramsJson={}", paramsJson);
        return R.success(paramsJson);
    }
}
*/
