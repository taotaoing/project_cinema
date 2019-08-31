package com.stylefeng.guns.api.user;


import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;

/**
 * @author 申涛涛
 * @date 2019/8/29 19:18
 */
public interface UserAPI {

    /*boolean login(String username,String password);

    boolean register(UserModelInfo userModelInfo);

    boolean checkUsername(String username);*/

    int login(String username,String password);
    boolean register(UserModel userModel);
    boolean checkUsername(String username);
    UserInfoModel getUserInfo(int uuid);
    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

}
