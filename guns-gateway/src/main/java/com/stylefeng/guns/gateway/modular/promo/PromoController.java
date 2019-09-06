package com.stylefeng.guns.gateway.modular.promo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.promo.PromoServiceAPI;
import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.gateway.modular.auth.util.TokenUtil;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/3 22:22
 */
@Slf4j
@RestController
@RequestMapping("/promo")
public class PromoController {
    @Reference(interfaceClass = PromoServiceAPI.class,check = false)
    PromoServiceAPI promoServiceAPI;

    @Autowired
    TokenUtil tokenUtil;




    @RequestMapping("/getPromo")
    public ResponseVO getPromo() {

        List<PromoInfoVO> promoInfo = promoServiceAPI.getPromoInfo();
        return ResponseVO.success(promoInfo);
    }

    /**
     *  秒杀下单接口
     * @creator shentaotao
     * @creat date 2019/9/5 15:48
     * @param promoId   秒杀id
     * @param amount    下单数量
     * @param request
     * @param response
     * @return com.stylefeng.guns.gateway.modular.vo.ResponseVO
     * @throws
     * @since
     */
    @RequestMapping("/createOrder")
    public ResponseVO createOrder(Integer promoId, Integer amount,
                                  HttpServletRequest request, HttpServletResponse response) {
        //查询用户是否登录
        ResponseVO responseVO = tokenUtil.getUserId(request, response);
        if (responseVO == null || 3 == responseVO.getStatus() || StringUtils.isBlank(responseVO.getMsg())) {
            log.info("获取用户失败，请重新登录！ResponseVO:{}",responseVO);
            return ResponseVO.serviceFail("获取用户失败，请重新登录！");
        }
        String userId = responseVO.getMsg();
        Integer uid = Integer.parseInt(userId);

        if (amount < 0 || amount > 10) {
            return ResponseVO.serviceFail("输入的数量不合法！");
        }

        try {
            //初始化库存流水，得到流水ID
            String stockLogId = promoServiceAPI.initPromoStockLog(promoId,amount);
            if (StringUtils.isBlank(stockLogId)) {
                throw new GunsException(GunsExceptionEnum.STOCK_LOG_INIT_ERROR);
            }
            promoServiceAPI.transactionSavePromoOrderVO(promoId,amount,uid,stockLogId);

        } catch (Exception e) {
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }
        return ResponseVO.success("下单成功！");
    }

}
