package com.stylefeng.guns.rest.modular.auth.validator.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.UserMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.model.User;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.auth.validator.dto.Credence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Override
    public boolean validate(Credence credence) {
        //判断用户名和密码
        List<MtimeUserT> users = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", credence.getCredenceName())
                                                                                .eq("user_pwd", MD5Util.encrypt(credence.getCredenceCode())));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
