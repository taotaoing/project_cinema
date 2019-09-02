package com.stylefeng.guns.rest.film.filmService;

import com.stylefeng.guns.rest.film.filmVo.BannerVo;
import com.stylefeng.guns.rest.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.rest.film.filmVo.FilmInfo;
import com.stylefeng.guns.rest.film.filmVo.FilmVo;

import java.util.List;

public interface FilmIndexService {
//    //获取banners
//    List<BannerVo> getBanners();
//
//    //获取热门影片
//    FilmVo getHotFilms();
//
//    //获取即将上映影片（受欢迎程度做排序）
//    FilmVo getSoonFilms();
//
//    //获取票房排行榜
//    List<FilmInfo> getBoxRanking();
//
//    //获取人气排行榜
//    List<FilmInfo> getExpectRanking();
//
//    //获取top100
//    List<FilmInfo> getTop();

    FilmIndexVo getFilmInfo();
}
