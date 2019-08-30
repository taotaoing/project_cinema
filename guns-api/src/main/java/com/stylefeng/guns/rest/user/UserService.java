package com.stylefeng.guns.rest.user;

import com.stylefeng.guns.rest.user.vo.UserModelInfo;
import com.stylefeng.guns.rest.user.vo.UserVO;

/**
 * @author 申涛涛
 * @date 2019/8/29 19:18
 */
public interface UserService {

    boolean login(String username,String password);

    boolean register(UserModelInfo userModelInfo);

    boolean checkUsername(String username);

}
