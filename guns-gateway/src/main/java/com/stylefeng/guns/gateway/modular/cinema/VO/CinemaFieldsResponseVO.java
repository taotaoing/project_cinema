package com.stylefeng.guns.gateway.modular.cinema.VO;

import com.stylefeng.guns.api.cinema.VO.CinemaInfo;
import com.stylefeng.guns.api.cinema.VO.FilmInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/4 8:10
 */
@Data
public class CinemaFieldsResponseVO {
    private CinemaInfo CinemaInfo;
    private List<FilmInfo> filmList;
}
