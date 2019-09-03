package com.stylefeng.guns.order.modular.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderInfoVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    //判断座位号是否存在
    @Override
    public boolean isTrueSeats(Integer fieldId, String soldSeats) {
        String ids = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24";
        if(soldSeats.contains(",")){
            String[] split = soldSeats.split(",");
            for (String s:split) {
                if(!ids.contains(s)){
                    return false;
                }
            }
        } else if(!ids.contains(soldSeats)){
            return false;
        }
        return true;
    }

    //判断座位是否已售出
    @Override
    public boolean isSoldSeats(Integer fieldId, String soldSeats) {
        String[] seats = moocOrderTMapper.selectSeatsByFieldId(fieldId);
        ArrayList<Object> list = new ArrayList<>();
        for (String s:seats) {
            if(s.contains(",")){
                String[] split = s.split(",");
                list.add(split);
            }
            list.add(s);
        }
        if (soldSeats.contains(",")){
            String[] split = soldSeats.split(",");
            for(String s:split){
                if(!list.contains(s)){
                    return true;
                }
            }
        }else if(!list.contains(soldSeats)){
            return true;
        }
        return false;
    }

    //下单
    @Override
    public OrderInfoVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }


    @Override
    public OrderInfoVO getOrderInfoById(String orderId) {
        MoocOrderT moocOrderT = moocOrderTMapper.selectById(orderId);
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setOrderId(orderId);
        return orderInfoVO;
    }

    //订单支付成功以后状态值改为1
    @Override
    public boolean paySuccess(String orderId) {
        return false;
    }
}
