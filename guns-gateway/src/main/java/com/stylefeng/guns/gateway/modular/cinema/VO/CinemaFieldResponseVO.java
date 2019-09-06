package com.stylefeng.guns.gateway.modular.cinema.VO;

import com.stylefeng.guns.api.cinema.VO.CinemaInfo;
import com.stylefeng.guns.api.cinema.VO.FilmInfo;
import com.stylefeng.guns.api.cinema.VO.HallInfo;
import lombok.Data;

/**
 * @author 申涛涛
 * @date 2019/9/4 8:24
 */
@Data
public class CinemaFieldResponseVO {
    private FilmInfo FilmInfo;
    private CinemaInfo CinemaInfo;
    private HallInfo HallInfo;
}
