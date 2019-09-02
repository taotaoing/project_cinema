package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 申涛涛
 * @date 2019/9/2 9:29
 */
@Data
public class OrderInfoVO implements Serializable {

    private static final long serialVersionUID = -5365081505596100068L;
    private String orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private String orderPrice;
    private String orderTimestamp;
    private String orderStatus;

}
