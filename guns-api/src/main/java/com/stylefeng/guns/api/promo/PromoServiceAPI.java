package com.stylefeng.guns.api.promo;

import com.stylefeng.guns.api.promo.vo.PromoInfoVO;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/3 20:38
 */
public interface PromoServiceAPI {
    List<PromoInfoVO> getPromoInfo();

    boolean checkAmount(Integer promoId, Integer amount);

    boolean savePromoInfo(Integer promoId, Integer amount, Integer userId);
}
