package com.stylefeng.guns.promo.modular.promo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.promo.PromoServiceAPI;
import com.stylefeng.guns.api.promo.vo.PromoInfoVO;
import com.stylefeng.guns.api.promo.vo.PromoOrderVO;
import com.stylefeng.guns.core.custom.StockLogStatus;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoOrderMapper;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.promo.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromo;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromoOrder;
import com.stylefeng.guns.promo.common.persistence.model.MtimePromoStock;
import com.stylefeng.guns.promo.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.promo.modular.mq.MqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 申涛涛
 * @date 2019/9/3 21:29
 */
@Slf4j
@Component
@Service(interfaceClass = PromoServiceAPI.class)
public class PromoServiceImpl implements PromoServiceAPI {

    @Autowired
    MtimePromoMapper mtimePromoMapper;
    @Autowired
    MtimePromoOrderMapper mtimePromoOrderMapper;
    @Autowired
    MtimePromoStockMapper mtimePromoStockMapper;
    @Autowired
    MtimeStockLogMapper stockLogMapper;
    @Autowired
    MqProducer mqProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;

    //设置兑换码有效期为3个月
    private static final String EXCHANGE_CODE_VALID_MONTH = "91";

    @Override
    public List<PromoInfoVO> getPromoInfo() {
        List<PromoInfoVO> promoInfoVO = mtimePromoMapper.getPromoInfoVO();
        return promoInfoVO;
    }

    @Override
    public boolean checkAmount(Integer amount,Integer promoId) {
        MtimePromoStock mtimePromoStock = new MtimePromoStock();
        mtimePromoStock.setPromoId(promoId);
        MtimePromoStock mtimePromoStock1 = mtimePromoStockMapper.selectOne(mtimePromoStock);
        if (mtimePromoStock1 != null) {
            if (amount > mtimePromoStock1.getStock()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean savePromoInfo(Integer promoId, Integer amount, Integer userId) {
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);
        MtimePromoOrder mtimePromoOrder = new MtimePromoOrder();
        mtimePromoOrder.setCinemaId(mtimePromo.getCinemaId());
        mtimePromoOrder.setPrice(mtimePromo.getPrice());
        mtimePromoOrder.setAmount(amount);
        mtimePromoOrder.setUserId(userId);

        Integer insert = mtimePromoOrderMapper.insert(mtimePromoOrder);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<PromoInfoVO> getPromoByCinemaId(Integer cinemaId) {
        return null;
    }

    /**
     * 初始化库存流水
     * @creator shentaotao
     * @creat date 2019/9/5 16:08
     * @param promoId
     * @param amount
     * @return java.lang.String
     * @throws
     * @since
     */
    @Override
    public String initPromoStockLog(Integer promoId, Integer amount) {
        MtimeStockLog mtimeStockLog = new MtimeStockLog();
        mtimeStockLog.setAmount(amount);
        mtimeStockLog.setPromoId(promoId);
        String uuid = String.valueOf(UUID.randomUUID());
        mtimeStockLog.setStatus(StockLogStatus.INIT);

        Integer insert = stockLogMapper.insert(mtimeStockLog);
        if (insert > 0) {
            return uuid;
        }

        return null;
    }

    @Override
    public Boolean transactionSavePromoOrderVO(Integer promoId, Integer amount, Integer uid, String stockLogId) {
        Boolean ret = mqProducer.transactionSaveOrder(promoId,amount,uid,stockLogId);
        return ret;
    }

    /**
     * 生成秒杀订单
     * 扣减库存
     * 扣钱
     * @creator shentaotao
     * @creat date 2019/9/5 20:10
     * @param promoId
     * @param amount
     * @param userId
     * @param stockLogId
     * @return com.stylefeng.guns.api.promo.vo.PromoOrderVO
     * @throws
     * @since
     */
    @Override
    public PromoOrderVO savePromoOrderVO(Integer promoId, Integer amount, Integer userId, Integer stockLogId) {
        //参数校验
        processParam(promoId,amount,userId);
        MtimePromo mtimePromo = mtimePromoMapper.selectById(promoId);

        //订单入库
        MtimePromoOrder promoOrder = savePromoOrder(mtimePromo,amount,userId);

        //扣减库存
        Boolean ret = decreaseStock(promoId,amount);
        //查询流水库存
        MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
        if (!ret){
            mtimeStockLog.setStatus(StockLogStatus.FAIL);
            Integer update = stockLogMapper.updateById(mtimeStockLog);

            throw new GunsException(GunsExceptionEnum.STOCK_ERROR);
        }
        mtimeStockLog.setStatus(StockLogStatus.SUCCESS);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("uuid",stockLogId);
        Integer update = stockLogMapper.update(mtimeStockLog, entityWrapper);
        if (update <= 0) {
            //将amount放回redis
            increaseStock(promoId,amount);
            throw new GunsException(GunsExceptionEnum.STOCK_ERROR);
        }

        //组装参数返回前端
        PromoOrderVO promoOrderVO =  buildPromoOrderVO(promoOrder);


        //返回前端
        return promoOrderVO;
    }

    //构建返回的参数
    private PromoOrderVO buildPromoOrderVO(MtimePromoOrder promoOrder) {
        PromoOrderVO orderVO = new PromoOrderVO();
        orderVO.setUuid(promoOrder.getUuid());
        orderVO.setUserId(promoOrder.getUserId());
        orderVO.setEndTime(promoOrder.getEndTime());
        orderVO.setStartTime(promoOrder.getStartTime());
        orderVO.setAmount(promoOrder.getAmount());
        orderVO.setCinemaId(promoOrder.getCinemaId());
        orderVO.setCreateTime(promoOrder.getCreateTime());
        orderVO.setExchangeCode(promoOrder.getExchangeCode());
        orderVO.setPrice(promoOrder.getPrice().doubleValue());
        return orderVO;
    }

    private Boolean increaseStock(Integer promoId, Integer amount) {
        Long increment = redisTemplate.opsForValue().increment("promo1", amount);
        return true;
    }

    /**
     * 1.标志REQUIRES_NEW会新开启事务，外层事务不会影响内部事务的提交/回滚
     * 2.标志REQUIRES_NEW的内部事务的异常，会影响外部事务的回滚
     * 事务的传播机制 Propagation.REQUIRES_NEW
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean decreaseStock(Integer promoId, Integer amount) {
        Long increment = redisTemplate.opsForValue().increment("promo1", amount * (-1));
        if (increment < 0) {
            return false;
        }
        return true;
    }

    //生成秒杀的订单
    private MtimePromoOrder savePromoOrder(MtimePromo mtimePromo, Integer amount, Integer userId) {
        MtimePromoOrder promoOrder = buildPromoOrder(mtimePromo,amount,userId);
        Integer insert = mtimePromoOrderMapper.insert(promoOrder);
        if (insert < 1) {
            log.info("生成秒杀订单失败！ promoOrder:{}",promoOrder);
            return null;
        }
        return promoOrder;
    }

    //类型生成 MtimePromo --> MtimePromoOrder
    private MtimePromoOrder buildPromoOrder(MtimePromo mtimePromo, Integer amount, Integer userId) {
        MtimePromoOrder promoOrder = new MtimePromoOrder();
        String uuid = String.valueOf(UUID.randomUUID());
        Integer cinemaId = mtimePromo.getCinemaId();
        String exchangeCode = String.valueOf(UUID.randomUUID());

        //兑换开始时间和兑换结束时间 为从现在开始，到未来三个月之内
        Date startTime = new Date();
        //Date endTime = DateUtil.getAfterDayDate(EXCHANGE_CODE_VALID_MONTH);
        BigDecimal amountDecimal = new BigDecimal(amount);
        BigDecimal price = amountDecimal.multiply(mtimePromo.getPrice()).setScale(2, RoundingMode.HALF_UP);
        promoOrder.setUuid(uuid);
        promoOrder.setUserId(userId);
        promoOrder.setCinemaId(cinemaId);
        promoOrder.setExchangeCode(exchangeCode);
        promoOrder.setStartTime(startTime);
        //promoOrder.setEndTime(endTime);
        promoOrder.setAmount(amount);
        promoOrder.setPrice(price);
        promoOrder.setCreateTime(new Date());
        return  promoOrder;

    }

    //参数校验
    private void processParam(Integer promoId, Integer amount, Integer userId) {
        if (promoId == null) {
            log.info("promoId 不能为空");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        if (amount == null) {
            log.info("amount 不能为空");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        if (userId == null) {
            log.info("userId 不能为空");
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
    }
}
