package com.framework.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 *
 */
public class Constant {

    public enum IssuingMgrStatusEnum {
        UNCHECK(1, "待复核"),
        CHECKED(2, "已复核待处理"),
        REFUSED(3, "复核拒绝"),
        SUCCESS(4, "代付处理完成"),
        FAILED(5, "代付处理失败");
        private int value;
        private String name;
        IssuingMgrStatusEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }
        public int getValue() {
            return value;
        }
        public String getName() {
            return name;
        }
    }
}
