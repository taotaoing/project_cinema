package com.stylefeng.guns.api.promo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 申涛涛
 * @date 2019/9/5 20:04
 */
@Data
public class PromoOrderVO implements Serializable {
    /**
     * 主键id
     */
    private String uuid;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 影院id
     */
    private Integer cinemaId;
    /**
     * 兑换码
     */
    private String exchangeCode;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 订单总金额
     */
    private Double price;
    /**
     * 兑换开始时间
     */
    private Date startTime;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 兑换结束时间
     */
    private Date endTime;
}
