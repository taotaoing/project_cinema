/*
package com.stylefeng.guns.cinema.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.cinema.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.cinema.common.persistence.dao.MtimeUserTMapper;
import UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-29
 *//*

@Component
@Service(interfaceClass = UserService.class)
public class MtimeUserTServiceImpl implements UserService {
    @Autowired
    MtimeUserTMapper mtimeUserTMapper;


    @Override
    public boolean insert(UserVO userVO) {
        MtimeUserT mtimeUserT = new MtimeUserT();
        BeanUtils.copyProperties(userVO,mtimeUserT);
        Integer insert = mtimeUserTMapper.insert(mtimeUserT);
        if (insert > 1) {
            return true;
        }
        return false;
    }
}
*/
