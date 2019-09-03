package com.stylefeng.guns.api.promo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 申涛涛
 * @date 2019/9/3 21:02
 */
@Data
public class PromoInfoVO implements Serializable {

    private String cinemaAddress;
    private Integer cinemaId;
    private String cinemaName;
    private String  description;
    private Date endTime;
    private String imgAddress;
    private Integer price;
    private Date startTime;
    private Integer status;
    private Integer stock;
    private Integer uuid;
}
