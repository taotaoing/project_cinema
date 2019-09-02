package com.stylefeng.guns.order.common.persistence.dao;

import com.stylefeng.guns.api.order.vo.OrderInfoVO;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-09-02
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {
    String[] selectSeatsByFieldId(@Param("fieldId") Integer fieldId);
}
