package com.stylefeng.guns.order.modular.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderInfoVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
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


    @Override
    public OrderInfoVO getOrderInfoById(String orderId) {
        MoocOrderT moocOrderT = moocOrderTMapper.selectById(orderId);
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setOrderId(orderId);

        return orderInfoVO;
    }

    @Override
    public boolean paySuccess(String orderId) {
        return false;
    }

    @Override
    public boolean payFail(String orderId) {
        return false;
    }
}
