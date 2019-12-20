package com.framework.pay.service;

abstract class AbstractPayCallbackService {
    abstract String afterCallbackExcuter(String orderNo, String cashFee, String transactionId, String cutomerId);
}
