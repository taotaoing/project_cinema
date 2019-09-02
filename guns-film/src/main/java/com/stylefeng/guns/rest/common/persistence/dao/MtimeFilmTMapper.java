package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.api.film.filmVo.FilmInfo;

import java.util.List;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-08-29
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {
    int countHotFilms();

    List<FilmInfo> selectFilms();

    int countSoonFilms();
}
