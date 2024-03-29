package com.framework.AESMethorDemo;

import java.lang.annotation.*;

/**
 * 返回对body加密,针对类跟方法
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseEncrypt {

    /**
     * 返回对body加密，默认是true
     * @author 溪云阁
     * @return boolean
     */
    boolean value() default true;
}
