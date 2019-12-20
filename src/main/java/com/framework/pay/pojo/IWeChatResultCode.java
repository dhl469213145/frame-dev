/**
 * Copyright (c) 2016-2019 湖南神雀 All rights reserved.
 * <p>
 * https://www.sqqmall.com
 */
package com.framework.pay.pojo;

import java.io.Serializable;

/**
 * 微信支付返回结果
 * @author yijun
 * @version 1.0  2016年11月9日下午3:28:03
 */
public interface IWeChatResultCode extends Serializable {
    /**
     * 消息
     *
     * @return String
     */
    String getMsg();

    /**
     * 状态码
     *
     * @return String
     */
    String getCode();
}
