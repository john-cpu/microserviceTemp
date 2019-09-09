package com.base.mt.repository;

import com.base.mt.domain.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@Repository
public interface OrderRepository extends JpaRepository<SaleOrder,Long> {

    SaleOrder findOrderById(long id);
}
