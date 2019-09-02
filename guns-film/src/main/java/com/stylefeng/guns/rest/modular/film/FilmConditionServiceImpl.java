package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCatDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeSourceDictTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeYearDictTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCatDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeSourceDictT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeYearDictT;
import com.stylefeng.guns.api.film.filmService.FilmConditionService;
import com.stylefeng.guns.api.film.filmVo.CatInfoVo;
import com.stylefeng.guns.api.film.filmVo.FilmConditionVo;
import com.stylefeng.guns.api.film.filmVo.SourceInfoVo;
import com.stylefeng.guns.api.film.filmVo.YearInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmConditionService.class)
public class FilmConditionServiceImpl implements FilmConditionService {

    @Autowired
    MtimeCatDictTMapper mtimeCatDictTMapper;
    @Autowired
    MtimeSourceDictTMapper mtimeSourceDictTMapper;
    @Autowired
    MtimeYearDictTMapper mtimeYearDictTMapper;

    @Override
    public FilmConditionVo getFilmCondition(Integer catId, Integer sourceId, Integer yearId) {
        FilmConditionVo filmConditionVo = new FilmConditionVo();

        List<CatInfoVo> catInfoVos = getCatsBean(catId);
        List<SourceInfoVo> sourceInfoVos = getSourcesBean(sourceId);
        List<YearInfoVo> yearInfoVos = getYearsBean(yearId);

        filmConditionVo.setCatInfo(catInfoVos);
        filmConditionVo.setSourceInfo(sourceInfoVos);
        filmConditionVo.setYearInfo(yearInfoVos);
        return filmConditionVo;
    }

    private List<YearInfoVo> getYearsBean(Integer yearId) {
        ArrayList<YearInfoVo> list = new ArrayList<>();
        List<MtimeYearDictT> mtimeYearDictTS = mtimeYearDictTMapper.selectList(null);
        for (MtimeYearDictT m:mtimeYearDictTS) {
            YearInfoVo yearInfoVo = new YearInfoVo();
            yearInfoVo.setYearId(m.getUuid());
            if (yearId == null){
                yearId = 99;
            }
            if (yearId == yearInfoVo.getYearId()){
                yearInfoVo.setActive(true);
            }
            yearInfoVo.setYearName(m.getShowName());
            list.add(yearInfoVo);
        }
        return list;
    }

    private List<SourceInfoVo> getSourcesBean(Integer sourceId) {
        ArrayList<SourceInfoVo> list = new ArrayList<>();
        List<MtimeSourceDictT> mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(null);
        for (MtimeSourceDictT m: mtimeSourceDictTS) {
            SourceInfoVo sourceInfoVo = new SourceInfoVo();
            sourceInfoVo.setSourceId(m.getUuid());
            if (sourceId == null){
                sourceId = 99;
            }
            if(sourceId == sourceInfoVo.getSourceId()){
                sourceInfoVo.setActive(true);
            }
            sourceInfoVo.setSourceName(m.getShowName());
            list.add(sourceInfoVo);
        }
        return list;
    }

    private List<CatInfoVo> getCatsBean(Integer catId) {
        ArrayList<CatInfoVo> list = new ArrayList<>();
        List<MtimeCatDictT> mtimeCatDictTS = mtimeCatDictTMapper.selectList(null);
        for (MtimeCatDictT m: mtimeCatDictTS) {
            CatInfoVo catInfoVo = new CatInfoVo();
            catInfoVo.setCatId(m.getUuid());
            if (catId == null){
                catId = 99;
            }
            if(catId == catInfoVo.getCatId()){
                catInfoVo.setActive(true);
            }
            catInfoVo.setCatName(m.getShowName());
            list.add(catInfoVo);
        }
        return list;
    }
}
