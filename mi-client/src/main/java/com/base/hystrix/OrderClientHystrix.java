package com.base.hystrix;

import com.base.client.OrderClient;
import com.base.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@Component
public class OrderClientHystrix implements OrderClient {
    @Override
    public OrderDto getOrderById(Integer id) {
        return null;
    }

    @Override
    public List<OrderDto> getAll() {
        return Collections.emptyList();
    }

    @Override
    public void mq(Integer id) {

    }
}
