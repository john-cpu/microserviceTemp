package com.base.RabbitMQ.Send;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date 2019/9/5,
 * @since 2.0
 */
@Service
public class Sender implements RabbitTemplate.ReturnCallback{
    /* @Autowired
        AmqpTemplate amqpTemplate;*///原本使用的模板
   @Autowired
   RabbitTemplate rabbitTemplate;
    public void send(Integer id){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("发送时间：" + sf.format(new Date()));
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {//确认消息到达Exchange
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        /*rabbitTemplate.convertAndSend("my_exchange", "hello", id, message -> {
            message.getMessageProperties().setHeader("x-delay", 60000);
            return message;
        });*/
        JSONObject jsonObject = new JSONObject();
        String jsonString = jsonObject.toJSONString();
        Message message = MessageBuilder.withBody(jsonString.getBytes()) .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                .setMessageId(UUID.randomUUID() + "")
                .setHeader("x-delay",6000).build();

        rabbitTemplate.convertAndSend("mx-exchange","hello",id,message1 -> {return message;});
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {//确认消息是否到达队列
        System.out.println("消息主体 message : "+message);
        System.out.println("消息主体 message : "+replyCode);
        System.out.println("描述："+replyText);
        System.out.println("消息使用的交换器 exchange : "+exchange);
        System.out.println("消息使用的路由键 routing : "+routingKey);
    }

}

