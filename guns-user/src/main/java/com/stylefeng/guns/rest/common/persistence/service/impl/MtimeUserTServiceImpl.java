package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserModelInfo;
import com.stylefeng.guns.rest.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils.eq;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-29
 */
@Component
@Service(interfaceClass = UserService.class)
public class MtimeUserTServiceImpl implements UserService {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

      //登录
    @Override
    public boolean login(String username,String password) {
        String encrypt = MD5Util.encrypt(password);
        List<MtimeUserT> users = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", username)
                .eq("user_pwd", encrypt));
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean register(UserModelInfo userModelInfo) {
            MtimeUserT userT = new MtimeUserT();
            BeanUtils.copyProperties(userModelInfo,userT);
            Integer insert = mtimeUserTMapper.insert(userT);
            if(insert != 0){
                return true;
            }
        return false;
    }
    @Override
    public boolean checkUsername(String username) {
        List<MtimeUserT> users = mtimeUserTMapper.selectList(new EntityWrapper<MtimeUserT>().eq("user_name", username));
        //如果数据库中有有存在的用户名了
        if (users != null && users.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
