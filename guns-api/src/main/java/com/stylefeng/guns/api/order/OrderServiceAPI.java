package com.stylefeng.guns.api.order;

import com.stylefeng.guns.api.order.vo.OrderInfoVO;

/**
 * @author 申涛涛
 * @date 2019/9/2 9:23
 */
public interface OrderServiceAPI {

    //创建订单详情
    OrderInfoVO savaOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    //通过 orderId 查询订单详情
    OrderInfoVO getOrderInfoById(String orderId);

    boolean paySuccess(String orderId);

    boolean payFail(String orderId);
}
