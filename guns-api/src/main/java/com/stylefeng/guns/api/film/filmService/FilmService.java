package com.stylefeng.guns.api.film.filmService;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmService {

    // 获取影片
    List<FilmInfo> getFilms(int showType, int catId, int sourceId, int yearId);

    // 根据影片ID或者名称获取影片详情信息
    FilmDetailVO getFilmDetail(Integer searchType, String searchParam);
}
