package org.ecomm.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@Getter
@Setter
@ToString
@Where(clause = "is_deleted = false")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long productId;
    private Integer stock;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    private boolean isDeleted;
}




