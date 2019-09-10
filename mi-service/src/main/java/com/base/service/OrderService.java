package com.base.service;

import com.base.dto.OrderDto;
import com.base.mt.domain.SaleOrder;
import com.base.mt.repository.OrderRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@Service
public class OrderService {
    private static Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    OrderRepository orderRepository;

    public OrderDto getOrderById(Integer id){
        SaleOrder order = new SaleOrder();
        try{
            order = orderRepository.findOrderById((long)id);
        }catch (Exception e){
            log.error("获取订单失败",e);
        }
       return order != null? OrderDto.builder().id(order.getId()).money(order.getMoney()).name(order.getName()).state(order.getState()).build():null;
    }

    public List<OrderDto> getAll() {
        List<SaleOrder> orders = Lists.newArrayList();//Collections.emptyList()
        try{
            orders= orderRepository.findAll();
        }catch (Exception e){
            log.error("获取订单失败",e);
        }
        if(!CollectionUtils.isEmpty(orders)){
            return orders.stream().map(e->OrderDto.builder().name(e.getName()).money(e.getMoney()).id(e.getId()).state(e.getState()).build()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    @Transactional
    public void del(Integer id) {
        orderRepository.deleteById((long)id);
    }
}
