package com.framework.pay.service;


public abstract class AbstractPayCallbackService {

    public abstract String afterCallbackExcuter(String orderNo, String cashFee, String transactionId, String cutomerId);
}
