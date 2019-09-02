package com.stylefeng.guns.gateway.modular.alipay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 申涛涛
 * @date 2019/9/2 20:44
 */
@RestController
@RequestMapping("/order")
public class AliPayController {
    private static final String IMG_PRE = "http://";

    @Reference(interfaceClass = AliPayServiceAPI.class,check = false)
    AliPayServiceAPI aliPayServiceAPI;

    @RequestMapping(value = "getPayInfo",method = RequestMethod.POST)
    public ResponseVO getPayInfo(@Param("orderId") String orderId) {
        //获取当前登录的用户信息
        String userId = CurrentUser.getUserId();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("用户未登录");
        }
        //获取返回的二维码
        AliPayInfoVO qrCode = aliPayServiceAPI.getQRCode(orderId);
        if (qrCode != null) {
            return ResponseVO.success(IMG_PRE,qrCode);
        }
        return ResponseVO.serviceFail("订单支付失败，请稍后重试");
    }

    @RequestMapping(value = "getPayResult",method = RequestMethod.POST)
    public ResponseVO getPayInfo(@RequestParam("orderId") String orderId,
                                 @RequestParam(value = "tryNum",required = false,defaultValue = "1")Integer tryNum) {
        //获取当前登录的用户信息
        String userId = CurrentUser.getUserId();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("用户未登录");
        }

        if (tryNum >= 4) {
            return ResponseVO.serviceFail("订单支付失败，请稍后重试");
        } else {
            AliPayResultVO aliPayResultVO = aliPayServiceAPI.getOrderStatus(orderId);
            if (aliPayResultVO == null || aliPayResultVO.getOrderId().trim().length() > 0) {
                AliPayResultVO aliPayResultVO1 = new AliPayResultVO();
                aliPayResultVO1.setOrderStatus(0);
                aliPayResultVO1.setOrderId(orderId);
                aliPayResultVO1.setOrderMsg("订单支付失败，请稍后重试");
                return ResponseVO.success(aliPayResultVO1);
            }
            return ResponseVO.success(aliPayResultVO);
        }
    }
}
