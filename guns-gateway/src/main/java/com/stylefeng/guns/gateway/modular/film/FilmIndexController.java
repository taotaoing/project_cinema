package com.stylefeng.guns.gateway.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.filmService.FilmConditionService;
import com.stylefeng.guns.api.film.filmService.FilmIndexService;
import com.stylefeng.guns.api.film.filmService.FilmService;
import com.stylefeng.guns.api.film.filmVo.FilmConditionVo;
import com.stylefeng.guns.api.film.filmVo.FilmIndexVo;
import com.stylefeng.guns.api.film.vo.FilmDetailVO;
import com.stylefeng.guns.api.film.vo.FilmInfoVO;
import com.stylefeng.guns.api.film.vo.FilmRequestVO;
import com.stylefeng.guns.gateway.modular.film.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/film")
public class FilmIndexController {
    @Reference(interfaceClass = FilmIndexService.class,check = false)
    private FilmIndexService filmIndexService;
    @Reference(interfaceClass = FilmConditionService.class,check = false)
    private FilmConditionService filmConditionService;
    @Reference(interfaceClass = FilmService.class,check = false)
    private FilmService filmService;

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

    // 影片查询
    @RequestMapping(value = "getFilms", method = RequestMethod.GET)
    public Map<String, Object> getFilms(FilmRequestVO filmRequestVO) {
        List<FilmInfoVO> films = filmService.getFilms(filmRequestVO.getShowType(), filmRequestVO.getCatId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId());
        Map<String, Object> responseMsg = new HashMap<>();
        responseMsg.put("status", 0);
        responseMsg.put("imgPre", "http://img.meetingshop.cn/");
        responseMsg.put("nowPage", filmRequestVO.getNowPage());
        responseMsg.put("totalPage", films.size());
        responseMsg.put("data", films);
        return responseMsg;
    }

    //影片详情查询
    // films/狮子王  films/2
    @RequestMapping(value = "films/{searchParam}", method = RequestMethod.GET)
    public Map<String, Object> getFilmDetails(Integer searchType, @PathVariable String searchParam) {
        FilmDetailVO filmDetailVO = filmService.getFilmDetail(searchType, searchParam);
        Map<String, Object> responseMsg = new HashMap<>();
        responseMsg.put("status", 0);
        responseMsg.put("imgPre", "http://img.meetingshop.cn/");
        responseMsg.put("data", filmDetailVO);
        return responseMsg;
    }
}
