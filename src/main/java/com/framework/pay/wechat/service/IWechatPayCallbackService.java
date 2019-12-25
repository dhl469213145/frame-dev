package com.framework.pay.wechat.service;

import com.framework.pay.wechat.pojo.PayCallbackDTO;

public interface IWechatPayCallbackService {

    // 用户自己去实现相关回调业务
    Boolean doCallbackService(PayCallbackDTO payCallbackDTO);
}
