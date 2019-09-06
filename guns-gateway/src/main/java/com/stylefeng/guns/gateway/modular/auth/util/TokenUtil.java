package com.stylefeng.guns.gateway.modular.auth.util;


import com.stylefeng.guns.gateway.config.properties.JwtProperties;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author 申涛涛
 * @date 2019/9/4 22:55
 */
@Slf4j
@Component
public class TokenUtil {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    JwtProperties jwtProperties;
    //redis 数据过期时间
    private static final Integer expireTime = 60 * 60;

    public ResponseVO getUserId(HttpServletRequest request, HttpServletResponse response) {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String authToken = requestHeader.substring(7);
            //通过token获取userId

            String userId = jwtTokenUtil.getUsernameFromToken(authToken);

            if (StringUtils.isBlank(userId)) {
                return null;
            }
            //将 userId 存入缓存
            redisTemplate.opsForValue().set(authToken,userId,expireTime, TimeUnit.SECONDS);
            return ResponseVO.success(userId);

        } else {
            return null;
        }
    }

    /**
     * 删除 redis 中的缓存 token
     * @creator shentaotao
     * @creat date 2019/9/5 15:18
     * @param request
     * @param response
     * @return java.lang.Boolean
     * @throws
     * @since
     */
    public Boolean delTokenFromCache(HttpServletRequest request,HttpServletResponse response){
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        Boolean isDelete = false;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String authToken = requestHeader.substring(7);
            isDelete = redisTemplate.delete(authToken);
        }
        return isDelete;
    }
}
