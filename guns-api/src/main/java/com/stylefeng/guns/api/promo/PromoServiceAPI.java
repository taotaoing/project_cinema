package com.stylefeng.guns.api.promo;

import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.api.promo.vo.PromoOrderVO;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/3 20:38
 */
public interface PromoServiceAPI {
    List<PromoInfoVO> getPromoInfo();
    //查询 库存是否充足
    boolean checkAmount(Integer promoId, Integer amount);
    //创建订单
    boolean savePromoInfo(Integer promoId, Integer amount, Integer userId);

    //根据 影院id 查询秒杀订单列表
    List<PromoInfoVO> getPromoByCinemaId(Integer cinemaId);

    String initPromoStockLog(Integer promoId, Integer amount);

    Boolean transactionSavePromoOrderVO(Integer promoId, Integer amount, Integer uid, String stockLogId);

    PromoOrderVO savePromoOrderVO(Integer promoId, Integer amount, Integer userId, Integer stockLogId);
}
