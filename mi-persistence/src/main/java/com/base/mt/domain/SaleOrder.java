package com.base.mt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhanghonglin
 * @version 2.0
 * @Date ,
 * @since 2.0
 */
@Entity
@Table(name = "saleOrder")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MONEY")
    private Double money;
    @Column(name = "STATE")
    private Integer state;
}
