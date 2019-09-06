package com.stylefeng.guns.gateway.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.auth.util.TokenUtil;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 申涛涛
 * @date 2019/8/29 19:23
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserAPI.class, check = false)
    UserAPI userAPI;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseVO register(UserModel userModel) {
        if (userModel.getUsername() == null || userModel.getUsername().trim().length() == 0) {
            return ResponseVO.serviceFail("用户名不能为空");
        }
        if (userModel.getPassword() == null || userModel.getPassword().trim().length() == 0) {
            return ResponseVO.serviceFail("密码不能为空");
        }
        boolean register = userAPI.register(userModel);
        if (register) {
            return ResponseVO.success("注册成功");
        } else {
            return ResponseVO.serviceFail("注册失败");
        }
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public ResponseVO check(String username) {
        if (username != null && username.trim().length() > 0) {
            //返回为true的时候表示用户名可用
            boolean b = userAPI.checkUsername(username);
            if (b) {
                return ResponseVO.success("验证成功");
            } else {
                return ResponseVO.serviceFail("用户已存在");
            }
        } else {
            return ResponseVO.serviceFail("用户名不能为空");
        }
    }

    /**
     * @creator shentaotao
     * @creat date 2019/9/5 11:52
     * @param   username    登录用户名
     * @param   password    登录用户密码
     * @return com.stylefeng.guns.gateway.modular.vo.ResponseVO
     * @throws
     * @since
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseVO login(String username, String password) {
        if (StringUtils.isAnyBlank(username,password)) {
            log.info("用户名或密码不能为空");
            return ResponseVO.serviceFail("用户名或密码不能为空");
        }

        int login = userAPI.login(username, password);
        if (login > 0) {
            log.info("登录成功");
            return ResponseVO.success("登录成功");
        }

        return ResponseVO.serviceFail("登录失败");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseVO logout(HttpServletRequest request, HttpServletResponse response) {
        Boolean aBoolean = tokenUtil.delTokenFromCache(request, response);
        if (aBoolean) {
            return ResponseVO.success("成功退出");
        }
        return ResponseVO.success("退出失败");
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public ResponseVO getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        //获取用户 userId
        ResponseVO responseVO = tokenUtil.getUserId(request, response);
        // 3 表示过期
        if (3 == responseVO.getStatus()) {
            return ResponseVO.expire();
        }
        String userId = responseVO.getMsg();
        if (StringUtils.isBlank(userId)) {
            UserInfoModel userInfo = userAPI.getUserInfo(Integer.parseInt(userId));
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.serviceFail("查询失败");
            }
        }else {
            return ResponseVO.serviceFail("用户尚未登陆");
        }
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel, HttpServletRequest request, HttpServletResponse response) {
        //获取用户 userId
        ResponseVO responseVO = tokenUtil.getUserId(request, response);
        // 3 表示过期
        if (3 == responseVO.getStatus()) {
            return ResponseVO.expire();
        }
        String userId = responseVO.getMsg();
        if (StringUtils.isBlank(userId)) {
            Integer uuid = Integer.valueOf(userId);
            //判断修改userId是否为当前登录的用户
            if (uuid != userInfoModel.getUuid()) {
                return ResponseVO.serviceFail("不能修改其他用户的信息");
            }
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.serviceFail("查询失败");
            }
        }else {
            return ResponseVO.serviceFail("用户尚未登陆");
        }
    }














    /*@Reference(interfaceClass = UserService.class, check = false)
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
    }*/
}
