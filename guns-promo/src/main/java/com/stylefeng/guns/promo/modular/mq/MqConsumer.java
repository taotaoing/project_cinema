package com.stylefeng.guns.promo.modular.mq;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.promo.common.persistence.dao.MtimePromoStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/9/5 17:48
 */
@Slf4j
@Component
public class MqConsumer {
    @Value("${mq.nameserver.address}")
    private String namesrcAddress;

    @Value("${mq.topic}")
    private String topic;

    @Autowired
    private MtimePromoStockMapper stockMapper;

    DefaultMQPushConsumer consumer;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("stock_consumer");
        consumer.setNamesrvAddr(namesrcAddress);
        consumer.subscribe(topic,"*");


        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                MessageExt messageExt = msgs.get(0);
                byte[] body = messageExt.getBody();
                String bodyString = new String(body);

                HashMap<String,Object> hashMap = JSON.parseObject(bodyString, HashMap.class);
                Integer promoId = (Integer)hashMap.get("promoId");
                Integer amount = (Integer)hashMap.get("amount");

                //扣减本地库存
                Integer affectRows = stockMapper.decreaseStock(promoId, amount);
                if (affectRows > 0) {
                    log.info("消息消费成功！promoId:{}, amount:{}",promoId,amount);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }else {
                    log.info("消息消费失败！promoId:{}, amount:{}",promoId,amount);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        consumer.start();
    }
}
