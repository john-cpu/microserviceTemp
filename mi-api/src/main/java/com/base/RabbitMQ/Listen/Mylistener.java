package com.base.RabbitMQ.Listen;

import com.base.dto.OrderDto;
import com.base.service.OrderService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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

    @Autowired
    RedisTemplate redisTemplate;
    /*在使用Spring RabbitMQ做消息监听时，如果监听程序处理异常了，且未对异常进行捕获，会一直重复接收消息，然后一直抛异常。
    * 设置requeue-rejected为false来丢弃消息
    * */
    @RabbitHandler
    public void process(Integer id, Channel channel, Message message){
        try{
            if(null != id){
                //如果存在且为"",则表示已处理 否则未处理
                String messageid = id.toString();//把传递的唯一之值作为key 重复传即重复操作
                if(!redisTemplate.hasKey(messageid)){
                    redisTemplate.opsForValue().set(messageid,message.getMessageProperties().getMessageId());
                }
                if(Objects.nonNull(redisTemplate.opsForValue().get(messageid))&&redisTemplate.opsForValue().get(messageid)!=""){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println("接收时间:" + sdf.format(new Date()));
                    System.out.println("messageid:" + message.getMessageProperties().getMessageId());
                    //1.如果日志系统数据库中此messageid为已经处理 直接return
                    //2.如果redisTemplate.opsForValue().get(messageid)="" 则表示已经处理 return 否则未处理
                    OrderDto orderDto = orderService.getOrderById(id);
                    if(orderDto != null && orderDto.getState()==2){
                        try{
                            orderService.del(id);
                            System.out.println("删除完毕where id=" + id);
                            //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                            redisTemplate.opsForValue().set(messageid, "");
                            System.out.println("handle success");
                        }catch (Exception e){
                            //抛出异常即回滚
                            System.out.println("inner:"+e.getMessage());
                            //丢弃这条消息
                            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
                            e.printStackTrace();
                            System.out.println("handle fall");
                        }
                    }else{
                        redisTemplate.opsForValue().set("messageid", "");
                    }
                }else {
                    //丢弃这条消息
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
                }
            }
        }catch (Exception e){
            System.out.println("outter:"+e.getMessage());
        }
    }
}
