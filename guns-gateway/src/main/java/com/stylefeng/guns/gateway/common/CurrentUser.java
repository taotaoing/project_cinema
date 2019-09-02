package com.stylefeng.guns.gateway.common;

/**
 * @author 申涛涛
 * @date 2019/8/31 11:01
 */
public class CurrentUser {
    //通过线程绑定存储用户的 userId
    public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        threadLocal.set(userId);
    }
    public static String getUserId() {
        return threadLocal.get();
    }
}
