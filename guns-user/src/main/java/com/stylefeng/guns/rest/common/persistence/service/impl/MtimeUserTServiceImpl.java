package com.stylefeng.guns.rest.common.persistence.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.user.UserService;
import com.stylefeng.guns.rest.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
