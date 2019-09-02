/*
package com.stylefeng.guns.rest.modular.auth.controller;

import com.stylefeng.guns.core.exception.GunsException;
import ResponseVo;
import BizExceptionEnum;
import AuthRequest;
import JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

*/
/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 *//*

@RestController
public class AuthController {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVo<?> createAuthenticationToken(AuthRequest authRequest) {

        //调用自己的方法实现登录验证
        boolean validate = userService.login(authRequest.getUserName(),authRequest.getPassword());
        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("randomKey",randomKey);
            hashMap.put("token",token);
            return ResponseVo.success(hashMap);
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
*/
