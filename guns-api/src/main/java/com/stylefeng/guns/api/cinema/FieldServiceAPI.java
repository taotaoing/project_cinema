package com.stylefeng.guns.api.cinema;


import com.stylefeng.guns.api.cinema.VO.getFieldInfo.FieldInfo;
import com.stylefeng.guns.api.cinema.VO.getFields.Fields;

public interface FieldServiceAPI {
    Fields searchFieldsByCinemaId(int cinemaId);

    FieldInfo searchFieldInfoByCinemaIdAndFieldId(int cinemaId, int fieldId);
}
