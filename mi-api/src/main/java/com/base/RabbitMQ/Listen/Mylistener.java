package com.base.RabbitMQ.Listen;

import com.base.dto.OrderDto;
import com.base.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date 2019/9/5,
 * @since 2.0
 */
@Component
@RabbitListener(queues = "my_queue")
public class Mylistener {
    @Autowired
    OrderService orderService;
    /*在使用Spring RabbitMQ做消息监听时，如果监听程序处理异常了，且未对异常进行捕获，会一直重复接收消息，然后一直抛异常。
    * 设置requeue-rejected为false来丢弃消息
    * */
    @RabbitHandler
    public void process(Integer id, Channel channel, Message message){
        try{
            if(null != id){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("接收时间:" + sdf.format(new Date()));
                OrderDto orderDto = orderService.getOrderById(id);
                if(orderDto != null && orderDto.getState()==2){
                    try{
                        orderService.del(id);
                        System.out.println("删除完毕where id=" + id);
                        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                        System.out.println("receiver success");
                    }catch (Exception e){
                        System.out.println("inner"+e.getMessage());
                        //丢弃这条消息
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
                        e.printStackTrace();
                        System.out.println("receiver fall");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("outter:"+e.getMessage());
        }
    }
}
