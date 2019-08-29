package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserVO;
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

    @RequestMapping("/insert")
    public String insertUser(UserVO userVO) {
        boolean insert = userService.insert(userVO);
        if (insert) {
            return "insert success";
        }
        return "insert fail";
    }
}
