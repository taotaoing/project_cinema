package com.stylefeng.guns.cinema.persistence.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.api.cinema.VO.getFields.Fields;
import com.stylefeng.guns.cinema.persistence.model.MtimeFieldT;

/**
 * <p>
 * 放映场次表 服务类
 * </p>
 *
 * @author dml
 * @since 2019-08-29
 */
public interface IMtimeFieldTService extends IService<MtimeFieldT> {
    Fields searchFieldsByCinemaId(int cinemaId);

}
