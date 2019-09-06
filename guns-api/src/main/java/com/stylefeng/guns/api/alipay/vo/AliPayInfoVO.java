package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 申涛涛
 * @date 2019/9/2 17:46
 */
@Data
public class AliPayInfoVO implements Serializable {
    private String orderId;
    private String QRCodeAddress;
}
