package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.Respond.ResponseVo;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserModelInfo;
import com.stylefeng.guns.rest.user.vo.UserVO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 申涛涛
 * @date 2019/8/29 19:23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @RequestMapping("/register")
    public ResponseVo insertUser(UserModelInfo userModelInfo) {
        try {
            if (userModelInfo.getUserName() == null || userModelInfo.getUserPwd() == null) {
                return ResponseVo.Fail("用户名或者密码没有输入");
            }
            String username = userModelInfo.getUserName();
            if (userService.checkUsername(username)) {
                return ResponseVo.Fail("用户已存在");
            }
            boolean register = userService.register(userModelInfo);
            if (register == true) {
                return ResponseVo.success("注册成功");
            }else
            {
                return ResponseVo.Fail("未知错误");
            }
        } catch (Exception e) {
            return ResponseVo.unknown("系统出现异常，请联系管理员", 999);
        }
    }



    @RequestMapping("/check")
    public ResponseVo checkUser(String username) {
        try {
            if (username == null || StringUtils.isEmpty(username)) {
                return ResponseVo.Fail("用户名没有输入");
            }
            if (userService.checkUsername(username)) {
                return ResponseVo.Fail("用户已存在");
            } else {
                return ResponseVo.success("验证成功");
            }
        } catch (Exception e) {
            return ResponseVo.unknown("系统出现异常，请联系管理员", 999);
        }
    }
}
