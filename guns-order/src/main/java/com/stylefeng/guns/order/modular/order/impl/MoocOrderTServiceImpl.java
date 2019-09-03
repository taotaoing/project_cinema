package com.stylefeng.guns.order.modular.order.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.VO.FilmInfoVO;
import com.stylefeng.guns.api.cinema.VO.OrderQueryVO;
import com.stylefeng.guns.api.cinema.VO.OrderVO;
import com.stylefeng.guns.api.film.filmService.FilmConditionService;
import com.stylefeng.guns.api.film.filmService.FilmIndexService;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderInfoVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 */

@Component
@Service(interfaceClass = OrderServiceAPI.class)
public class MoocOrderTServiceImpl extends ServiceImpl<MoocOrderTMapper, MoocOrderT>  implements OrderServiceAPI {
    @Autowired
    MoocOrderTMapper moocOrderTMapper;
    @Reference(interfaceClass = CinemaServiceAPI.class, check = false)
    CinemaServiceAPI cinemaServiceAPI;
    @Reference(interfaceClass = FilmIndexService.class, check = false)
    FilmIndexService filmIndexService;
    @Reference(interfaceClass = FilmConditionService.class, check = false)
    FilmConditionService filmConditionService;

    @Override
    public boolean isTureSeats(String fieldId, String seats) {

        String seatsTotal = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24";
        if(seats.contains(",")){
            String[] split = seats.split(",");
            for (String s:split){
                if(!seatsTotal.contains(s)){
                    return false;
                }
            }
        }else {
            if (!seatsTotal.contains(seats)) {
                return false;
            }
        }
        return true;
    }
   //判断是否已售
    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("field_id",fieldId);

        List<MoocOrderT> list = moocOrderTMapper.selectList(entityWrapper);
        if(seats.contains(",")) {
            String[] wantBuyids = seats.split(",");
            for (MoocOrderT moocOrderT : list) {
                String[] ids = moocOrderT.getSeatsIds().split(",");
                for (String id : ids) {
                    for (String wantbuyid : wantBuyids) {
                        if (id.equals(wantbuyid)) {
                            return false;
                        }
                    }
                }
            }
        }else {
            for (MoocOrderT moocOrderT : list) {
                String[] ids = moocOrderT.getSeatsIds().split(",");
                for (String id : ids) {
                        if (id.equals(seats)) {
                            return false;
                        }
                    }
                }
            }
        return true;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        FilmInfoVO filmInfoVO = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
        Integer filmId = Integer.parseInt(filmInfoVO.getFilmId());
        String uuid = UUIDUtil.genUuid();
        // 获取影院信息
        OrderQueryVO orderQueryVO = cinemaServiceAPI.getOrderNeeds(fieldId);
        Integer cinemaId = Integer.parseInt(orderQueryVO.getCinemaId());
        double filmPrice = Double.parseDouble(orderQueryVO.getFilmPrice());

        // 求订单总金额  // 1,2,3,4,5
        int solds = soldSeats.split(",").length;
        double totalPrice = getTotalPrice(solds,filmPrice);

        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(uuid);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setOrderUser(userId);
        moocOrderT.setOrderPrice(totalPrice);
        moocOrderT.setFilmPrice(filmPrice);
        moocOrderT.setFilmId(filmId);
        moocOrderT.setFieldId(fieldId);
        moocOrderT.setCinemaId(cinemaId);

        Integer insert = moocOrderTMapper.insert(moocOrderT);
        if(insert>0){
            // 返回查询结果
            OrderVO orderVO = moocOrderTMapper.getOrderInfoById(uuid);
            if(orderVO == null || orderVO.getOrderId() == null){
                return null;
            }else {
                return orderVO;
            }
        }else{
            return null;
        }
    }
    private static double getTotalPrice(int solds,double filmPrice){
        BigDecimal soldsDeci = new BigDecimal(solds);
        BigDecimal filmPriceDeci = new BigDecimal(filmPrice);

        BigDecimal result = soldsDeci.multiply(filmPriceDeci);

        // 四舍五入，取小数点后两位
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> result = new Page<>();
        if(userId == null){
            return null;
        }else{
            List<OrderVO> ordersByUserId = moocOrderTMapper.getOrdersByUserId(userId,page);
            if(ordersByUserId==null && ordersByUserId.size()==0){
                result.setTotal(0);
                result.setRecords(new ArrayList<>());
                return result;
            }else{
                // 获取订单总数
                EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user",userId);
                Integer counts = moocOrderTMapper.selectCount(entityWrapper);
                // 将结果放入Page
                result.setTotal(counts);
                result.setRecords(ordersByUserId);
                return result;
            }
        }
    }

    // 根据放映查询，获取所有的已售座位

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if(fieldId == null){
            return "";
        }else{
            String soldSeatsByFieldId = moocOrderTMapper.getSoldSeatsByFieldId(fieldId);
            return soldSeatsByFieldId;
        }
    }

    @Override
    public OrderVO getOrderInfoById(String orderId) {
        OrderVO orderInfoById = moocOrderTMapper.getOrderInfoById(orderId);
        return orderInfoById;
    }

    @Override
    public boolean paySuccess(String OrderId) {
        MoocOrderT moocOrderT = moocOrderTMapper.selectById(OrderId);
       //1是成功
        moocOrderT.setOrderStatus(1);
        Integer integer = moocOrderTMapper.updateById(moocOrderT);
        if(integer > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean payFail(String OrderId) {
        MoocOrderT moocOrderT = moocOrderTMapper.selectById(OrderId);
        //0是失败
        moocOrderT.setOrderStatus(0);
        Integer integer = moocOrderTMapper.updateById(moocOrderT);
        if(integer > 0){
            return true;
        }
        return false;
  }
}

