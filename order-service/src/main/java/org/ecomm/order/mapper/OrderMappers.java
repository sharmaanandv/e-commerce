package org.ecomm.order.mapper;

import org.ecomm.order.domain.CreateOrder;
import org.ecomm.order.domain.Order;
import org.ecomm.order.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMappers {

    OrderMappers INSTANCE = Mappers.getMapper(OrderMappers.class);

    Order toOrder(OrderEntity orderEntity);

    OrderEntity toOrderEntity(CreateOrder createOrder);

}
