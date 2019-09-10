package com.anze.controller;

import com.base.client.OrderClient;
import com.base.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@RestController
@RequestMapping("/order")
//@CrossOrigin(origins = "http://localhost:4200")可接收前端angular请求
public class OrderController {

    @Autowired
    OrderClient orderClient;

    @GetMapping("getOrderById")
    public OrderDto getOrderById(@RequestParam("id")int id) {
        return orderClient.getOrderById(id);
    }
    @GetMapping("getAll")
    public List<OrderDto> getAll() {
        return orderClient.getAll();
    }
    @GetMapping("mq")
    public void mq(@RequestParam("id")Integer id) {
        orderClient.mq(id);
    }
}
