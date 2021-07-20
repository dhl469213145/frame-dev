package com.framework.utilDemo;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class IndexDemo {

    /**
     * 根据明文密码，随机生成salt，然后进行加密
     * @param pwd
     * @return
     */
    public static String ramdow(String pwd) {
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        return new Sha256Hash(pwd, salt).toHex();
    }

    public static void main(String[] args) {
        System.out.println(ramdow("dfsdf"));
    }
}
