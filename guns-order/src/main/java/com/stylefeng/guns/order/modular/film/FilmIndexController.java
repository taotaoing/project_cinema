package com.stylefeng.guns.order.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.filmService.FilmIndexService;
import com.stylefeng.guns.api.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.order.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmIndexController {

    @Reference(interfaceClass = FilmIndexService.class,check = false)
    private FilmIndexService filmService;

    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public ResponseVo getInfo(){
        FilmIndexVo filmInfo = filmService.getFilmInfo();
        return ResponseVo.success(filmInfo);
    }
}
