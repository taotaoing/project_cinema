package com.stylefeng.guns.rest.modular.cinema.VO;

import com.stylefeng.guns.rest.cinema.VO.AreaVO;
import com.stylefeng.guns.rest.cinema.VO.BrandVO;
import com.stylefeng.guns.rest.cinema.VO.HallTypeVO;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @auther dml
 * @date 2019/8/29 14:29
 */
@Data
public class CinemaListResponseVO {
    private List<BrandVO> brandList;
    private List<AreaVO> areaList;
    private List<HallTypeVO> halltypeList;
}
