package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MtimeActorT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmInfoT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.api.film.filmService.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    MtimeFilmTMapper filmMapper;

    @Autowired
    MtimeCatDictTMapper catDictTMapper;

    @Autowired
    MtimeSourceDictTMapper sourceDictTMapper;

    @Autowired
    MtimeFilmInfoTMapper filmInfoTMapper;

    @Autowired
    Mapper dozerBeanMapper;

    @Autowired
    MtimeActorTMapper actorTMapper;

    @Override
    public List<FilmInfo> getFilms(int showType, int catId, int sourceId, int yearId) {
        List<FilmInfo> films = filmMapper.getFilms(showType, sourceId, yearId);
        Iterator<FilmInfo> iterator = films.iterator();
        if (catId != 99) {
            while (iterator.hasNext()) {
                if (!iterator.next().getCatId().contains(String.valueOf(catId))) {
                    iterator.remove();
                }
            }
        }
        return films;
    }

    @Override
    public FilmDetailVO getFilmDetail(Integer searchType, String searchParam) {
        FilmDetail filmDetail = new FilmDetail();
        if (searchType == 0) {
            int param = Integer.parseInt(searchParam);
            filmDetail = filmMapper.getFilmDetailByFilmId(param);
        } else {
            filmDetail = filmMapper.getFilmDetailByFilmName(searchParam);
        }
        // 获得电影的分类，即为info01
        StringBuffer info01 = new StringBuffer();
        String[] split = filmDetail.getFilmCats().split("#");
        for(int i = 1; i < split.length; i++) {
            int id = Integer.parseInt(split[i]);
            MtimeCatDictT mtimeCatDictT = catDictTMapper.selectById(id);
            if (i != split.length - 1) {
                info01.append(mtimeCatDictT.getShowName());
                info01.append(", ");
            } else {
                info01.append(mtimeCatDictT.getShowName());
            }
        }
        // 获得电影的来源并拼接上电影时长，即为info02
        MtimeSourceDictT sourceDictT = sourceDictTMapper.selectById(filmDetail.getFilmSource());
        String info02 = sourceDictT.getShowName() + " / " + filmDetail.getFilmLength() + "分钟";
        // 获得电影的上映时间和地点
        String info03 = filmDetail.getFilmTime().substring(0, 10) + " " + sourceDictT.getShowName() + "上映";
        FilmDetailVO filmDetailVO = dozerBeanMapper.map(filmDetail, FilmDetailVO.class);
        // 获得电影的简介
        Map<String, Object> info04 = new HashMap<>();
        MtimeFilmInfoT mtimeFilmInfoT = filmInfoTMapper.selectById(filmDetail.getFilmId());
        info04.put("biography", mtimeFilmInfoT.getBiography());
        Map<String, Object> actorMap = new HashMap<>();
        // 获得电影的导演
        // MtimeActorT directorT = actorTMapper.selectById(mtimeFilmInfoT.getDirectorId());
        MtimeActorT directorT = actorTMapper.selectById(mtimeFilmInfoT.getDirectorId());
        ImgVO imgVO = new ImgVO();
        String[] imgSplit = mtimeFilmInfoT.getFilmImgs().split(",");
        imgVO.setMainImg(imgSplit[0]);
        imgVO.setImg01(imgSplit[1]);
        imgVO.setImg02(imgSplit[2]);
        imgVO.setImg03(imgSplit[3]);
        imgVO.setImg04(imgSplit[4]);

        Map<String, Object> director = new HashMap<>();
        director.put("imgAddress", directorT.getActorImg());
        director.put("directorName", directorT.getActorName());
        actorMap.put("director", director);
        // 获得电影的演员
        List actors = actorTMapper.queryActorInfosByFilmId(filmDetail.getFilmId());
        actorMap.put("actors",actors);
        info04.put("actors", actorMap);
        info04.put("imgVO",imgVO);
        info04.put("filmId",filmDetail.getFilmId());
        filmDetailVO.setScoreNum(filmDetailVO.getScoreNum() + "万人评分");
        filmDetailVO.setInfo01(info01.toString());
        filmDetailVO.setInfo02(info02);
        filmDetailVO.setInfo03(info03);
        filmDetailVO.setInfo04(info04);
        filmDetailVO.setImgVO(imgVO);
        return filmDetailVO;
    }
}
