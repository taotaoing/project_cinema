package com.stylefeng.guns.promo.common.persistence.dao;

import com.stylefeng.guns.promo.common.persistence.model.MtimePromoStock;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-03
 */
public interface MtimePromoStockMapper extends BaseMapper<MtimePromoStock> {

    Integer decreaseStock(@Param("promoId") Integer promoId,
                          @Param("amount") Integer amount);
}
