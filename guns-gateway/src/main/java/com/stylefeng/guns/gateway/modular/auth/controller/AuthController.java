package com.stylefeng.guns.gateway.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.gateway.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.gateway.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.gateway.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@Slf4j
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @Resource(name = "simpleValidator")
//    private IReqValidator reqValidator;
    @Reference(interfaceClass = UserAPI.class,check = false)
    UserAPI userAPI;

    private static ExecutorService executorService;
    //redis 中数据过期时间
    private static final Integer expireTime = 60 * 60;

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        executorService = new ThreadPoolExecutor(10,100,0l, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
    }

    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVO createAuthenticationToken(AuthRequest authRequest) {
        boolean validate = true;
        //boolean validate = reqValidator.validate(authRequest);

        int userId = userAPI.login(authRequest.getUserName(), authRequest.getPassword());
        if (userId == 0) {
            validate = false;
        }

        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken("" + userId, randomKey);

            executorService.execute(() -> {
                loginAfter(token,userId);
            });
            return ResponseVO.success(new AuthResponse(token, randomKey));
        } else {
            return ResponseVO.serviceFail("用户名或密码错误");
        }
    }

    private void loginAfter(String token, Integer userId) {
        if (userId == null) {
            userId = 0;
            redisTemplate.opsForValue().set(token,"" + 0,expireTime,TimeUnit.SECONDS);
            return;
        }
        redisTemplate.opsForValue().set(token,"" + userId,expireTime,TimeUnit.SECONDS);
        log.info("登录数据缓存成功，token: {}, userId: {}",token,userId);
    }
}
