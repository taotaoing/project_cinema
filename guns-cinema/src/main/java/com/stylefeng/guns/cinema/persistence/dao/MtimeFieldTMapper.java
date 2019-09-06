package com.stylefeng.guns.cinema.persistence.dao;

import com.stylefeng.guns.api.cinema.VO.FilmInfo;
import com.stylefeng.guns.api.cinema.VO.HallInfo;
import com.stylefeng.guns.cinema.persistence.model.MtimeFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author dml
 * @since 2019-08-29
 */
public interface MtimeFieldTMapper extends BaseMapper<MtimeFieldT> {

    List<FilmInfo> getFilmInfos(@Param("cinemaId") int cinemaId);

    HallInfo getHallInfo(@Param("fieldId") int fieldId);

    FilmInfo getFilmInfoById(@Param("fieldId") int fieldId);
}
