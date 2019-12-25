/**
 * Copyright (c) 2016-2019 湖南神雀 All rights reserved.
 * <p>
 * https://www.sqqmall.com
 */
package com.framework.pay.pojo;

import java.io.Serializable;

/**
 * 支付返回结果
 * @author dhl
 *
 */
public interface IPayResultCode extends Serializable {
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
