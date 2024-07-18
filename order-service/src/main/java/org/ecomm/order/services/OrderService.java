package org.ecomm.order.services;


import com.ecomm.comms.exception.EcommException;
import org.ecomm.order.domain.CreateOrder;
import org.ecomm.order.domain.Order;
import org.ecomm.order.domain.UpdateOrder;
import org.ecomm.order.entity.OrderEntity;
import org.ecomm.order.mapper.OrderMappers;
import org.ecomm.order.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMappers mappers;

    public OrderService(OrderRepository orderRepository, OrderMappers mappers) {
        this.orderRepository = orderRepository;
        this.mappers = mappers;
    }

    public List<Order> findAll() {
        return orderRepository.findAll().stream().map(mappers::toOrder).collect(Collectors.toList());
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).map(mappers::toOrder).orElseThrow(() -> new EcommException("Order not found"));
    }

    public Order createOrder(CreateOrder createOrder) {
        return mappers.toOrder(orderRepository.save(mappers.toOrderEntity(createOrder)));
    }

    @Transactional
    public Order updateOrder(UpdateOrder updateOrder) {
        throw new EcommException("Method not supported");
    }

    public void deleteById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            orderEntity.get().setDeleted(true);
            orderRepository.save(orderEntity.get());
        } else {
            throw new EcommException("Order not found");
        }
    }


}