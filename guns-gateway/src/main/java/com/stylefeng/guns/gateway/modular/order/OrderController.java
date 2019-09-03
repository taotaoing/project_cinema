package com.stylefeng.guns.gateway.modular.order;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.Respond.ResponseVo;
import com.stylefeng.guns.api.cinema.VO.OrderVO;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.gateway.common.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order/")
public class OrderController {

    private static TokenBucket tokenBucket = new TokenBucket();
    @Reference(interfaceClass = OrderServiceAPI.class, check = false)
    OrderServiceAPI orderServiceAPI;


    @RequestMapping(value = "buyTickets", method = RequestMethod.POST)
    public ResponseVo buyTickets(Integer fieldId, String soldSeats, String seatsName) {
        if(tokenBucket.getToken()){
            // 验证售出的票是否为真
            boolean isTrue = orderServiceAPI.isTureSeats(fieldId+"",soldSeats);

            // 已经销售的座位里，有没有这些座位
            boolean isNotSold = orderServiceAPI.isNotSoldSeats(fieldId+"",soldSeats);

            // 验证，上述两个内容有一个不为真，则不创建订单信息
            if(isTrue && isNotSold){
                // 创建订单信息,注意获取登陆人
                String userId = CurrentUser.getUserId();
                if(userId == null || userId.trim().length() == 0){
                    return ResponseVo.Fail("用户未登陆");
                }
                OrderVO orderVO = orderServiceAPI.saveOrderInfo(fieldId,soldSeats,seatsName,Integer.parseInt(userId));
                if(orderVO == null){
                    return ResponseVo.unknown("购票业务异常",999);
                }else{
                    return ResponseVo.success(orderVO);
                }
            }else{
                return ResponseVo.Fail("订单中的座位编号有问题");
            }
        }else{
            return ResponseVo.Fail("购票人数过多，请稍后再试");
        }
    }
    @RequestMapping(value = "getOrderInfo",method = RequestMethod.POST)
    public ResponseVo getOrderInfo(
            @RequestParam(name="nowPage",required = false,defaultValue = "1")Integer nowPage,
            @RequestParam(name="pageSize",required = false,defaultValue = "5")Integer pageSize){
        // 获取当前登陆人的信息
        String userId = CurrentUser.getUserId();

        // 使用当前登陆人获取已经购买的订单
        Page<OrderVO> page = new Page<>(nowPage,pageSize);
        if(userId != null && userId.trim().length()>0){
            Page<OrderVO> result = orderServiceAPI.getOrderByUserId(Integer.parseInt(userId), page);

            int totalPages = (int)(result.getPages());
            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());
            return ResponseVo.success(nowPage,totalPages,"",orderVOList);
        }else{
            return ResponseVo.Fail("用户未登陆");
        }
    }
}


