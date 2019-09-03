package com.stylefeng.guns.promo.modular.promo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.api.promo.PromoServiceAPI;
import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromo;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromoStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/3 21:29
 */
@Component
@Service(interfaceClass = PromoServiceAPI.class)
public class PromoServiceImpl implements PromoServiceAPI {

    @Autowired
    MtimePromoMapper mtimePromoMapper;
    @Autowired
    MtimePromoOrderMapper mtimePromoOrderMapper;
    @Autowired
    MtimePromoStockMapper mtimePromoStockMapper;

    @Override
    public List<PromoInfoVO> getPromoInfo() {
        List<PromoInfoVO> promoInfoVO = mtimePromoMapper.getPromoInfoVO();
        return promoInfoVO;
    }

    @Override
    public boolean checkAmount(Integer amount,Integer promoId) {
        MtimePromoStock mtimePromoStock = new MtimePromoStock();
        mtimePromoStock.setPromoId(promoId);
        MtimePromoStock mtimePromoStock1 = mtimePromoStockMapper.selectOne(mtimePromoStock);
        if (mtimePromoStock1 != null) {
            if (amount > mtimePromoStock1.getStock()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean savePromoInfo(Integer promoId, Integer amount, Integer userId) {
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);
        MtimePromoOrder mtimePromoOrder = new MtimePromoOrder();
        mtimePromoOrder.setCinemaId(mtimePromo.getCinemaId());
        mtimePromoOrder.setPrice(mtimePromo.getPrice());
        mtimePromoOrder.setAmount(amount);
        mtimePromoOrder.setUserId(userId);

        Integer insert = mtimePromoOrderMapper.insert(mtimePromoOrder);
        if (insert > 0) {
            return true;
        }
        return false;
    }
}
