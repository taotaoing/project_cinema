package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 申涛涛
 * @date 2019/9/2 17:47
 */
@Data
public class AliPayResultVO implements Serializable {
    private String orderId;
    private Integer orderStatus;
    private String orderMsg;
}
