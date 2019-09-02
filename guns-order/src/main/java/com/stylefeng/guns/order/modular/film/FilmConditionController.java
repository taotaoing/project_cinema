package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.filmService.FilmConditionService;
import com.stylefeng.guns.api.film.filmVo.FilmConditionVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmConditionController {
    @Reference(interfaceClass = FilmConditionService.class,check = false)
    private FilmConditionService filmConditionService;

    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public ResponseVo filmCondition(@RequestParam(name = "catId",required = false) Integer catId,
                                    @RequestParam(name = "sourceId",required = false)Integer sourceId,
                                    @RequestParam(name = "yearId",required = false)Integer yearId){
        FilmConditionVo filmCondition = filmConditionService.getFilmCondition(catId, sourceId, yearId);
        return ResponseVo.success(filmCondition);
    }
}
