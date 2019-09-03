package com.stylefeng.guns.api.order;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.VO.OrderVO;
import com.stylefeng.guns.api.order.vo.OrderInfoVO;

public interface OrderServiceAPI {
    //是否有座位
    boolean isTureSeats(String fieldId,String seats);
    //已经销售的有座位吗
    boolean isNotSoldSeats(String fieldId,String seats);

    OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    // 使用当前登陆人获取已经购买的订单
    Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page);

    // 根据FieldId 获取所有已经销售的座位编号
    String getSoldSeatsByFieldId(Integer fieldId);

    boolean paySuccess(String OrderId);

    boolean payFail(String OrderId);

}

