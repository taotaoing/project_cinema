package com.stylefeng.guns.promo.modular.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.api.promo.PromoServiceAPI;
import com.stylefeng.guns.api.promo.vo.PromoOrderVO;
import com.stylefeng.guns.core.custom.StockLogStatus;
import com.stylefeng.guns.promo.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.promo.common.persistence.model.MtimeStockLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * @author 申涛涛
 * @date 2019/9/5 17:46
 */
@Slf4j
@Component
public class MqProducer {

    @Value("${mq.nameserver.address}")
    private String namesrcAddress;
    @Value("${mq.topic}")
    private String topic;

    TransactionMQProducer transactionMQProducer;

    @Autowired
    PromoServiceAPI promoServiceAPI;
    @Autowired
    MtimeStockLogMapper stockLogMapper;

    @PostConstruct
    public void init() throws MQClientException {
        transactionMQProducer = new TransactionMQProducer("promo_transaction_producer");
        transactionMQProducer.setNamesrvAddr(namesrcAddress);
        transactionMQProducer.start();

        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object object) {
                HashMap hashMap = (HashMap) object;
                Integer promoId = (Integer) hashMap.get("promoId");
                Integer amount = (Integer) hashMap.get("amount");
                Integer userId = (Integer) hashMap.get("userId");
                Integer stockLogId = (Integer) hashMap.get("stockLogId");

                try {
                    PromoOrderVO promoOrderVO = promoServiceAPI.savePromoOrderVO(promoId, amount, userId, stockLogId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                byte[] body = msg.getBody();
                String bodyStr = new String(body);

                HashMap hashMap = JSON.parseObject(bodyStr, HashMap.class);
                String stockLogId = (String)hashMap.get("stockLogId");

                MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
                Integer status = mtimeStockLog.getStatus();
                if (StockLogStatus.FAIL == status) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }else if (StockLogStatus.SUCCESS == status) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }
        });
    }

    public Boolean asyncDecreaseStock(Integer promoId,Integer amount){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);
        byte[] msgBytes = JSON.toJSONString(hashMap).getBytes(Charset.forName("utf-8"));
        Message message = new Message(topic, msgBytes);

        SendResult ret = null;
        try {
            ret = transactionMQProducer.send(message);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        } catch (RemotingException e) {
            e.printStackTrace();
            return false;
        } catch (MQBrokerException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        log.info("发送库存扣减消息成功！ret :{}",JSON.toJSONString(ret));
        return true;
    }

    public Boolean transactionSaveOrder(Integer promoId, Integer amount, Integer uid, String stockLogId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);
        hashMap.put("userId",uid);
        hashMap.put("stockLogId",stockLogId);
        Message message = new Message("stock", JSON.toJSONString(hashMap).getBytes(Charset.forName("utf-8")));
        try {
            TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(message, hashMap);
        } catch (MQClientException e) {
            log.info("mq message 发送失败！{}",e);
            return false;
        }
        return true;
    }
}
