package org.ecomm.order.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Order {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer stock;
    private BigDecimal totalAmount;
}