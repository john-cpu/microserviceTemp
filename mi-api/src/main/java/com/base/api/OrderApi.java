package com.base.api;

import com.base.RabbitMQ.Send.Sender;
import com.base.client.OrderClient;
import com.base.dto.OrderDto;
import com.base.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@RestController
@RequestMapping("order")
public class OrderApi implements OrderClient {
    @Autowired
    OrderService orderService;

    @Autowired
    Sender sender;
    @Override
    @GetMapping("getOrderById")
    public OrderDto getOrderById(@RequestParam("id") Integer id) {
        return orderService.getOrderById(id);
    }

    @Override
    @GetMapping("getAll")
    public List<OrderDto> getAll() {
        return orderService.getAll();
    }

    @Override
    @GetMapping("mq")
    public void mq(@RequestParam("id") Integer id) {
        sender.send(id);
    }

}
