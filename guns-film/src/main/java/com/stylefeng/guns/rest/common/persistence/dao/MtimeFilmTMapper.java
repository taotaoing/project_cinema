package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.api.film.vo.FilmDetail;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<com.stylefeng.guns.api.film.filmVo.FilmInfo> selectFilms();

    int countSoonFilms();

    List<com.stylefeng.guns.api.film.vo.FilmInfo> getFilms(@Param("showType") int showType, @Param("sourceId") int sourceId, @Param("yearId") int yearId);

    FilmDetail getFilmDetailByFilmId(@Param("filmId") int filmId);

    FilmDetail getFilmDetailByFilmName(@Param("filmName") String filmName);
}
