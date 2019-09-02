package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;

/**
 * @author 申涛涛
 * @date 2019/9/2 14:55
 */
public interface AliPayServiceAPI {
    AliPayInfoVO getQRCode(String orderId);
    AliPayResultVO getOrderStatus(String orderId);

}
