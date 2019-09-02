package com.stylefeng.guns.api.order;

import com.stylefeng.guns.api.order.vo.OrderInfoVO;

public interface OrderServiceAPI {

    //验证预售的票是否为真
    boolean isTrueSeats(Integer fieldId, String soldSeats);

    //判断座位是否卖出
    boolean isSoldSeats(Integer fieldId,String soldSeats);
    //创建订单详情
    OrderInfoVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId);
}
