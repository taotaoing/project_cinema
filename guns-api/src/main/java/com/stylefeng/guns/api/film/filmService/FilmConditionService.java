package com.stylefeng.guns.api.film.filmService;

import com.stylefeng.guns.api.film.filmVo.FilmConditionVo;

public interface FilmConditionService {

    FilmConditionVo getFilmCondition(Integer catId, Integer sourceId, Integer yearId);

}
