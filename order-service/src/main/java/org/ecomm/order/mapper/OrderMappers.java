package org.ecomm.order.mapper;

import org.ecomm.order.domain.CreateOrder;
import org.ecomm.order.domain.Order;
import org.ecomm.order.domain.UpdateOrder;
import org.ecomm.order.entity.OrderEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMappers {

    OrderMappers INSTANCE = Mappers.getMapper(OrderMappers.class);

    Order toOrder(OrderEntity orderEntity);

    OrderEntity toOrderEntity(Order order);

    OrderEntity toOrderEntity(CreateOrder createOrder);

    OrderEntity toOrderEntity(UpdateOrder updateOrder);
}
