package com.stylefeng.guns.gateway.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.filmService.FilmConditionService;
import com.stylefeng.guns.api.film.filmService.FilmIndexService;
import com.stylefeng.guns.api.film.filmVo.FilmConditionVo;
import com.stylefeng.guns.api.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.gateway.modular.film.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmIndexController {
    @Reference(interfaceClass = FilmIndexService.class)
    private FilmIndexService filmIndexService;
    @Reference(interfaceClass = FilmConditionService.class)
    private FilmConditionService filmConditionService;

    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public ResponseVo index(){
        FilmIndexVo filmInfo = filmIndexService.getFilmInfo();
        return ResponseVo.success(filmInfo);
    }

    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public ResponseVo condition(@Param("catId") Integer catId,
                                @Param("sourceId") Integer sourceId,
                                @Param("yearId") Integer yearId){
        FilmConditionVo filmCondition = filmConditionService.getFilmCondition(catId, sourceId, yearId);
        return ResponseVo.success(filmCondition);
    }
}
