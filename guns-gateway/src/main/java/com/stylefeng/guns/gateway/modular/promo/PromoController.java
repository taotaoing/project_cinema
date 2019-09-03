package com.stylefeng.guns.gateway.modular.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.promo.PromoServiceAPI;
import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/3 22:22
 */
@RestController
@RequestMapping("/promo")
public class PromoController {
    @Reference(interfaceClass = PromoServiceAPI.class,check = false)
    PromoServiceAPI promoServiceAPI;

    @RequestMapping("/getPromo")
    public ResponseVO getPromo() {
        List<PromoInfoVO> promoInfo = promoServiceAPI.getPromoInfo();
        return ResponseVO.success(promoInfo);
    }

    @RequestMapping("/createOrder")
    public ResponseVO createOrder(Integer promoId, Integer amount) {
        //查询用户是否登录
        String userId = CurrentUser.getUserId();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("用户未登录");
        }
        Integer uid = Integer.parseInt(userId);
        //返回 true 表示amount合理或者库存充足
        boolean b1 = promoServiceAPI.checkAmount(promoId, amount);
        if (! b1) {
            return ResponseVO.serviceFail("输入的数量不合法或库存不足");
        }
        boolean b = promoServiceAPI.savePromoInfo(promoId, amount, uid);
        if (b) {
            return ResponseVO.success("下单成功");
        } else {
            return ResponseVO.serviceFail("下单失败");
        }
    }

}
