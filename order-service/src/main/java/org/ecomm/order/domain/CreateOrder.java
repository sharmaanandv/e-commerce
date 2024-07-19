package org.ecomm.order.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CreateOrder {

    @ReadOnlyProperty
    private Long userId;

    @NotBlank
    private Long productId;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private BigDecimal totalAmount;
}