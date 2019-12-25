package com.framework.pay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author Mark Wenjunchi
 */
public abstract class AbstractController {
//    @Autowired
//    JwtUtils jwtUtils;
    protected Logger logger = LoggerFactory.getLogger(getClass());

   /* protected Long getUserId(HttpServletRequest request) {
        Claims claimByToken = jwtUtils.getClaimByToken(request.getHeader(Constant.TOKEN));
        if (claimByToken == null) {
            throw new BizException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
        }
        return Long.parseLong(claimByToken.getSubject());


    }*/


}
