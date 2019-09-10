package com.base.RabbitMQ.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date 2019/9/5,
 * @since 2.0
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Queue my_queue(){
        return new Queue("my_queue");
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(my_queue()).to(defaultChange()).with("hello")/*.noargs()*/;
    }
    @Bean
    TopicExchange defaultChange(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type","direct");
        return new TopicExchange("my_exchange",true,false,args);
        //return new DirectExchange("my_exchange",true,false,args);
        //return new CustomExchange("my_exchange","x-delayed-message",true,false,args);
    }

}
