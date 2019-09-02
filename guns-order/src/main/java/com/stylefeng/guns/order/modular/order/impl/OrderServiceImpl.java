package com.stylefeng.guns.order.modular.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderInfoVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 申涛涛
 * @date 2019/9/2 14:28
 */
@Component
@Service(interfaceClass = OrderServiceAPI.class)
public class OrderServiceImpl implements OrderServiceAPI {
    @Autowired
    MoocOrderTMapper moocOrderTMapper;

    @Reference(interfaceClass = OrderServiceAPI.class, check = false)
    OrderServiceAPI orderServiceAPI;

    @Override
    public OrderInfoVO savaOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        OrderInfoVO orderInfoVO = new OrderInfoVO();


        return null;
    }
}
