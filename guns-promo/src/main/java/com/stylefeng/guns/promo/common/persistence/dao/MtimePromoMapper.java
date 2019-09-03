package com.stylefeng.guns.promo.common.persistence.dao;

import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimePromoMapper extends BaseMapper<MtimePromo> {
    List<PromoInfoVO> getPromoInfoVO();
}
