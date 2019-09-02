package com.stylefeng.guns.user.modular.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.user.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.user.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 申涛涛
 * @date 2019/8/31 10:58
 */
@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Override
    public int login(String userName, String password) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUserName(userName);
        //根据用户名去数据库查询账号信息
        MtimeUserT result = mtimeUserTMapper.selectOne(mtimeUserT);

        if (result != null && result.getUuid() > 0) {
            //通过md5加密后查看是否与数据库查出的结果一致
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password)) {
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        //将传入的数据转为数据库所需要的实体
        mtimeUserT.setUserName(userModel.getUsername());
        mtimeUserT.setEmail(userModel.getEmail());
        mtimeUserT.setAddress(userModel.getAddress());
        mtimeUserT.setUserPhone(userModel.getPhone());

        String md5Password = MD5Util.encrypt(userModel.getPassword());
        mtimeUserT.setUserPwd(md5Password);

        Integer insert = mtimeUserTMapper.insert(mtimeUserT);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUsername(String username) {
        EntityWrapper<MtimeUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",username);
        Integer integer = mtimeUserTMapper.selectCount(entityWrapper);
        if (integer != null && integer > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        //通过uuid查询用户信息
        MtimeUserT mtimeUserT = mtimeUserTMapper.selectById(uuid);
        //将数据库查询出的实体转为前端所需的实体数据
        UserInfoModel userInfoModel = do2UserInfo(mtimeUserT);
        return userInfoModel;
    }

    private UserInfoModel do2UserInfo(MtimeUserT mtimeUserT) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUuid(mtimeUserT.getUuid());
        userInfoModel.setUsername(mtimeUserT.getUserName());
        userInfoModel.setNickname(mtimeUserT.getNickName());
        userInfoModel.setSex(mtimeUserT.getUserSex());
        userInfoModel.setBirthday(mtimeUserT.getBirthday());
        userInfoModel.setEmail(mtimeUserT.getEmail());
        userInfoModel.setPhone(mtimeUserT.getUserPhone());
        userInfoModel.setAddress(mtimeUserT.getAddress());
        userInfoModel.setHeadAddress(mtimeUserT.getHeadUrl());
        userInfoModel.setBiography(mtimeUserT.getBiography());
        userInfoModel.setLifeState(mtimeUserT.getLifeState());
        userInfoModel.setCreatTime(mtimeUserT.getBeginTime());
        userInfoModel.setUpdateTime(mtimeUserT.getUpdateTime());
        return userInfoModel;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        mtimeUserT.setUuid(userInfoModel.getUuid());
        mtimeUserT.setUserName(userInfoModel.getUsername());
        mtimeUserT.setNickName(userInfoModel.getNickname());
        mtimeUserT.setUserSex(userInfoModel.getSex());
        mtimeUserT.setBirthday(userInfoModel.getBirthday());
        mtimeUserT.setEmail(userInfoModel.getEmail());
        mtimeUserT.setUserPhone(userInfoModel.getPhone());
        mtimeUserT.setAddress(userInfoModel.getAddress());
        mtimeUserT.setHeadUrl(userInfoModel.getHeadAddress());
        mtimeUserT.setBiography(userInfoModel.getBiography());
        mtimeUserT.setLifeState(userInfoModel.getLifeState());
        Integer integer = mtimeUserTMapper.updateById(mtimeUserT);
        if (integer > 0) {
            //修改成功后查出该数据返回给前端
            UserInfoModel userInfo = getUserInfo(mtimeUserT.getUuid());
            return userInfo;
        }else {
            return null;
        }
    }
}
