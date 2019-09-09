package com.base.client;

import com.base.dto.OrderDto;
import com.base.hystrix.OrderClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@FeignClient(value = "mi-api",fallback = OrderClientHystrix.class)
public interface OrderClient {
    @GetMapping("/order/getOrderById")
    OrderDto getOrderById(@RequestParam("id")Integer id);

    @GetMapping("/order/getAll")
    List<OrderDto> getAll();

    @GetMapping("/order/mq")
    void mq(@RequestParam("id") Integer id);
}
