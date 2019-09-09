package com.base.service;

import com.base.dto.OrderDto;
import com.base.mt.domain.SaleOrder;
import com.base.mt.repository.OrderRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return orders.stream().map(e->OrderDto.builder().name(e.getName()).money(e.getMoney()).id(e.getId()).build()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    public boolean isSubsequence(String a,String b){//b为大串
        if(b != null && !b.isEmpty()){
            if(null == a || a.isEmpty()){
                return true;
            }
            int alen = b.length();
            int i=0,j=0;
            while(i<alen){
                if(b.charAt(j)==b.charAt(i)){
                    j++;
                }
                i++;
                if(j == a.length()){
                    return true;
                }
            }
        }
        return false;
    }

    public void del(Integer id) {
        orderRepository.deleteById((long)id);
    }
    /*
    *
    public List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        int[] visited = new int[nums.length];
        backtrack(res, nums, new ArrayList<Integer>(), visited);
        return res;

    }

    private void backtrack(List<List<Integer>> res, int[] nums, ArrayList<Integer> tmp, int[] visited) {
        if (tmp.size() == nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1) continue;
            visited[i] = 1;
            tmp.add(nums[i]);
            backtrack(res, nums, tmp, visited);
            visited[i] = 0;
            tmp.remove(tmp.size() - 1);
        }
}
    * */
}
