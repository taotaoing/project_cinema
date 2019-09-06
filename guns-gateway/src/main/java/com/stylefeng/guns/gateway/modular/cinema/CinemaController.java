package com.stylefeng.guns.gateway.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.FieldServiceAPI;
import com.stylefeng.guns.api.cinema.VO.*;
import com.stylefeng.guns.api.cinema.VO.getFieldInfo.FieldInfo;
import com.stylefeng.guns.api.cinema.VO.getFields.Fields;
import com.stylefeng.guns.gateway.modular.cinema.VO.CinemaFieldResponseVO;
import com.stylefeng.guns.gateway.modular.cinema.VO.CinemaFieldsResponseVO;
import com.stylefeng.guns.gateway.modular.cinema.VO.CinemaListResponseVO;
import com.stylefeng.guns.gateway.modular.cinema.VO.ResponseVO;
import com.stylefeng.guns.gateway.modular.cinema.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @auther dml
 * @date 2019/8/29 14:05
 */
@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass = CinemaServiceAPI.class,check = false)
    private CinemaServiceAPI cinemaServiceAPI;

    private static final String IMG_PRE = "http://img.meetingshop.cn/";

    @RequestMapping(value = "getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO){
        try{
            // 按照五个条件进行筛选
            Page<CinemaVO> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVO);
            // 判断是否有满足条件的影院
            if(cinemas.getRecords() == null || cinemas.getRecords().size()==0){
                return ResponseVO.success("没有影院可查");
            }else{
                return ResponseVO.success(cinemas.getCurrent(),(int)cinemas.getPages(),IMG_PRE,cinemas.getRecords());
            }

        }catch (Exception e){
            // 如果出现异常，应该如何处理
            log.error("获取影院列表异常",e);
            return ResponseVO.serviceFail("查询影院列表失败");
        }
    }

    @RequestMapping(value = "getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO){
        try{
            // 获取三个集合，然后封装成一个对象返回即可
            List<BrandVO> brands = cinemaServiceAPI.getBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areas = cinemaServiceAPI.getAreas(cinemaQueryVO.getDistrictId());
            List<HallTypeVO> hallTypes = cinemaServiceAPI.getHallTypes(cinemaQueryVO.getHallType());

            CinemaListResponseVO cinemaListResponseVO = new CinemaListResponseVO();
            cinemaListResponseVO.setAreaList(areas);
            cinemaListResponseVO.setBrandList(brands);
            cinemaListResponseVO.setHalltypeList(hallTypes);

            return ResponseVO.success(cinemaListResponseVO);
        }catch (Exception e) {
            log.error("获取条件列表失败", e);
            return ResponseVO.serviceFail("获取影院查询条件失败");
        }
    }




    @Reference(interfaceClass= FieldServiceAPI.class, check = false)
    private FieldServiceAPI fieldServiceAPI;


    @RequestMapping("getFields")
    public ResponseVO getFields(Integer cinemaId) {
        if (cinemaId == null || cinemaId <= 0) {
            return ResponseVO.serviceFail("输入的 cinemaId 不合法");
        }
        try {
            CinemaInfo cinemaInfo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            List<FilmInfo> filmList = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);
            CinemaFieldsResponseVO cinemaFieldsResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldsResponseVO.setCinemaInfo(cinemaInfo);
            cinemaFieldsResponseVO.setFilmList(filmList);

            return ResponseVO.success(IMG_PRE, cinemaFieldsResponseVO);
        } catch (Exception e) {
            log.info("影院信息查询失败",e);
            return ResponseVO.serviceFail("影院信息查询失败");
        }

    }


    /*public HashMap getFields(int cinemaId){
        Fields fields = fieldServiceAPI.searchFieldsByCinemaId(cinemaId);
        int status;
        if(fields!=null){
            status = 0;
        }
        else  {
            status=1;
        }
        HashMap map = Result.response(status, fields);
        return map;
    }*/

    @RequestMapping("getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        if (cinemaId == null || fieldId == null) {
            return ResponseVO.serviceFail("请求的 cinemaId 或 fieldId 不合法");
        }
        try {
            CinemaInfo cinemaInfo = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfo filmInfo = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            HallInfo hallInfo = cinemaServiceAPI.getFilmFieldInfo(fieldId);

            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfo);
            cinemaFieldResponseVO.setFilmInfo(filmInfo);
            cinemaFieldResponseVO.setHallInfo(hallInfo);

            return ResponseVO.success(IMG_PRE, cinemaFieldResponseVO);
        } catch (Exception e) {
            log.info("获取场次信息失败",e);
            return ResponseVO.serviceFail("获取场次信息失败");
        }
    }


    /*public HashMap getFieldInfo(int cinemaId,int fieldId){
        FieldInfo fieldInfo = fieldServiceAPI.searchFieldInfoByCinemaIdAndFieldId(cinemaId,fieldId);
        int status;
        if(fieldInfo!=null){
            status = 0;
        }
        else  {
            status=1;
        }
        HashMap map = Result.response(status, fieldInfo);
        return map;
    }*/

}
