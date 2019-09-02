package com.stylefeng.guns.rest.film.filmService;

import com.stylefeng.guns.rest.film.filmVo.FilmConditionVo;

public interface FilmConditionService {

    FilmConditionVo getFilmCondition(Integer catId,Integer sourceId,Integer yearId);

}
