package org.ecomm.order.repo;


import org.ecomm.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUserId(long id);

    Optional<OrderEntity> findByIdAndUserId(Long id, long userId);
}