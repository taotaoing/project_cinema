package com.stylefeng.guns.api.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 申涛涛
 * @date 2019/8/31 10:54
 */
@Data
public class UserModel implements Serializable {
    private static final long serialVersionUID = -6720370536344710272L;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户住址
     */
    private String address;
}
