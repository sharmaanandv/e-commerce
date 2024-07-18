package org.ecomm.product.domain;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateProduct {

    private String name;
    private BigDecimal price;
    private Integer stock;
}