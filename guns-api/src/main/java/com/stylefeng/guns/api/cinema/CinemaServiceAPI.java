package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.VO.*;
import com.stylefeng.guns.api.cinema.VO.*;

import java.util.List;

/**
 * @Description:
 * @auther dml
 * @date 2019/8/29 14:13
 */

public interface CinemaServiceAPI {

    //1、根据CinemaQueryVO，查询影院列表
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);
    //2、根据条件获取品牌列表[除了就99以外，其他的数字为isActive]
    List<BrandVO> getBrands(int brandId);
    //3、获取行政区域列表
    List<AreaVO> getAreas(int areaId);
    //4、获取影厅类型列表
    List<HallTypeVO> getHallTypes(int hallType);

}
