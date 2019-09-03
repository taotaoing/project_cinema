package com.stylefeng.guns.cinema.persistence.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.cinema.FieldServiceAPI;
import com.stylefeng.guns.api.cinema.VO.getFieldInfo.FieldInfo;
import com.stylefeng.guns.api.cinema.VO.getFieldInfo.FilmInfo;
import com.stylefeng.guns.api.cinema.VO.getFieldInfo.HallInfo;
import com.stylefeng.guns.api.cinema.VO.getFields.CinemaInfo;
import com.stylefeng.guns.api.cinema.VO.getFields.Fields;
import com.stylefeng.guns.api.cinema.VO.getFields.FilmField;
import com.stylefeng.guns.api.cinema.VO.getFields.FilmList;
import com.stylefeng.guns.cinema.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.cinema.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.cinema.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.cinema.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.cinema.persistence.model.MtimeFieldT;
import com.stylefeng.guns.cinema.persistence.dao.MtimeFieldTMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.cinema.persistence.model.MtimeHallDictT;
import com.stylefeng.guns.cinema.persistence.model.MtimeHallFilmInfoT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 放映场次表 服务实现类
 * </p>
 *
 * @author dml
 * @since 2019-08-29
 */
@Component
@Service(interfaceClass = FieldServiceAPI.class)
public class MtimeFieldTServiceImpl extends ServiceImpl<MtimeFieldTMapper, MtimeFieldT> implements FieldServiceAPI {
    /**
     * 查询场次信息表
     */
    @Autowired
    MtimeCinemaTMapper cinemaTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper hallFilmInfoTMapper;

    @Autowired
    MtimeFieldTMapper fieldTMapper;

    @Override
    public Fields searchFieldsByCinemaId(int cinemaId) {
        Fields fields = new Fields();
        CinemaInfo cinemaInfo = getCinemaInfo(cinemaId);
        List<FilmList> filmList = getFilmList(cinemaId);
        fields.setCinemaInfo(cinemaInfo);
        fields.setFilmList(filmList);
        return fields;
    }

    private CinemaInfo getCinemaInfo(int cinemaId) {
        CinemaInfo cinemaInfo = new CinemaInfo();
        MtimeCinemaT mtimeCinemaT = cinemaTMapper.selectById(cinemaId);

        BeanUtils.copyProperties(mtimeCinemaT,cinemaInfo);
        return cinemaInfo;
    }

    private List<FilmList> getFilmList(int cinemaId) {
        List<FilmList> filmLists = new ArrayList<>();

        List<MtimeHallFilmInfoT> hallFilmInfoTS = hallFilmInfoTMapper.selectList(new EntityWrapper<>());
        for (MtimeHallFilmInfoT hallFilmInfoT : hallFilmInfoTS) {
            FilmList filmList = new FilmList();
            BeanUtils.copyProperties(hallFilmInfoT,filmList);
            //查找场次信息
            filmList.setFilmFields(getFilmFields(cinemaId,filmList.getFilmId(),hallFilmInfoT.getFilmLanguage()));
            filmList.setFilmType(hallFilmInfoT.getFilmLanguage());//language赋予type
            filmLists.add(filmList);
        }
        return filmLists;
    }

    private List<FilmField> getFilmFields(int cinemaId, int filmId, String filmType) {
        List<FilmField> filmFields = new ArrayList<>();

        List<MtimeFieldT> fieldTS = fieldTMapper.selectList(
                new EntityWrapper<MtimeFieldT>().eq("cinema_id", cinemaId).eq("film_id",filmId));
        for (MtimeFieldT fieldT : fieldTS) {
            FilmField field = new FilmField();
            BeanUtils.copyProperties(fieldT,field);
            field.setLanguage(filmType);//加语言信息，数据库中没有
            filmFields.add(field);
        }
        return filmFields;
    }


    /**
     * 查询某场次详情表
     */
    @Autowired
    MtimeHallDictTMapper hallDictTMapper;

    @Override
    public FieldInfo searchFieldInfoByCinemaIdAndFieldId(int cinemaId, int fieldId) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setFilmInfo(getFilmInfo(fieldId));
        fieldInfo.setCinemaInfo(getCinemaInfo(cinemaId));
        fieldInfo.setHallInfo(getHallInfo(cinemaId,fieldId));
        return fieldInfo;
    }


    private FilmInfo getFilmInfo(int fieldId) {
        FilmInfo filmInfo = new FilmInfo();
        MtimeHallFilmInfoT t = new MtimeHallFilmInfoT();
        t.setFilmId(fieldId);
        MtimeHallFilmInfoT hallFilmInfoT = hallFilmInfoTMapper.selectOne(t);

        System.out.println(hallFilmInfoT);//输出特使selectOne能否查出

        BeanUtils.copyProperties(hallFilmInfoT,filmInfo);
        filmInfo.setFilmType(hallFilmInfoT.getFilmLanguage());//把language赋予fileTye
        return  filmInfo;

    }

    private HallInfo getHallInfo(int cinemaId, int fieldId) {
        HallInfo hallInfo = new HallInfo();
        MtimeFieldT t = new MtimeFieldT();
        t.setCinemaId(cinemaId);
        t.setFilmId(fieldId);
        MtimeFieldT fieldT = fieldTMapper.selectOne(t);
        //查询场次座位详情
        MtimeHallDictT hallDictT = hallDictTMapper.selectById(fieldT.getHallId());

        BeanUtils.copyProperties(fieldT,hallInfo);
        hallInfo.setHallFieldId(fieldT.getHallId());//hallId赋予hallFiledId
        hallInfo.setSeatFile(hallDictT.getSeatAddress()); //二次查询赋予
        hallInfo.setSoldSeats("1,4,6,7,8");
        return  hallInfo;
    }

}
