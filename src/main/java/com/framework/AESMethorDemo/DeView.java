package com.framework.AESMethorDemo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加密数据接口
 */
@SuppressWarnings("deprecation")
@Api(tags = { "web服务：加密数据接口" })
@RestController
@RequestMapping("view/deView")
public class DeView {

    @Value("${module.boots.response.aes.key}")
    private String key;

    /**
     * 获取加密数据
     */
    @ApiOperation(value = "获取加密数据")
    @GetMapping(value = "/getEncrypt", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @SneakyThrows(CommonRuntimeException.class)
    @ResponseEncrypt
    public String getEncrypt() {
        final GetEncryptVO vo = new GetEncryptVO();
        vo.setId("b037123c");
        vo.setUserName("xnwqr98urx");
        return vo.toString();
    }

    /**
     * 获取解密数据
     */
  /*  @ApiOperation(value = "获取解密数据")
    @GetMapping(value = "/getDecrypt", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg<String> getDecrypt(@RequestParam(value = "content") String content) {
        final String str = AesUtils.decrypt(content, key);
        return MsgUtils.buildSuccessMsg(str);
    }*/

}