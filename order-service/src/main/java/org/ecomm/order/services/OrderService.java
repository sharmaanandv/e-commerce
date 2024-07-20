package org.ecomm.order.services;

import com.ecomm.comms.config.SecurityContext;
import com.ecomm.comms.dto.Principal;
import com.ecomm.comms.exception.EcommException;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.order.domain.CreateOrder;
import org.ecomm.order.domain.Order;
import org.ecomm.order.domain.UpdateOrder;
import org.ecomm.order.entity.OrderEntity;
import org.ecomm.order.mapper.OrderMappers;
import org.ecomm.order.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMappers mappers;
    private final WebClient.Builder webClientBuilder;

    @Value("${product.baseurl}")
    private String productBaseUrl;

    @Value("${product.get-by-id-url}")
    private String productGetByIdUrl;

    public OrderService(OrderRepository orderRepository, OrderMappers mappers, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.mappers = mappers;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Order> findAll() {
        Principal principal = SecurityContext.getPrincipal();
        // User can fetch only his orders
        return orderRepository.findByUserId(principal.getId()).stream().map(mappers::toOrder).collect(Collectors.toList());
    }

    public Order findById(Long id) {
        Principal principal = SecurityContext.getPrincipal();
        // User can fetch only his orders
        return orderRepository.findByIdAndUserId(id, principal.getId()).map(mappers::toOrder).orElseThrow(() -> new EcommException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public Order createOrder(CreateOrder createOrder) {
        log.debug("Order creating: {}", createOrder);
        Principal principal = SecurityContext.getPrincipal();
        verifyProductId(principal.getToken(), createOrder.getProductId());
        createOrder.setUserId(principal.getId());
        OrderEntity order = orderRepository.save(mappers.toOrderEntity(createOrder));
        log.info("Order created: {}", order);
        return mappers.toOrder(order);
    }

    // Assumption: Order are made immutable, Let user create new order
    @Transactional
    public Order updateOrder(UpdateOrder updateOrder) {
        throw new EcommException(HttpStatus.NOT_IMPLEMENTED, "Method not supported");
    }

    public void deleteById(Long id) {
        log.debug("Deleting OrderId: {}", id);
        Principal principal = SecurityContext.getPrincipal();
        Optional<OrderEntity> orderEntity = orderRepository.findByIdAndUserId(id, principal.getId());
        if (orderEntity.isPresent()) {
            orderEntity.get().setDeleted(true);
            orderRepository.save(orderEntity.get());
            log.info("Order deleted: {}", id);
        } else {
            throw new EcommException(HttpStatus.NOT_FOUND, "Order not found");
        }
    }

    // Check with product-service if ID is correct
    private void verifyProductId(String token, Long productId) {
        log.info("verifyProductId : {}", productId);
        String response = webClientBuilder.build()
                .get()
                .uri(productBaseUrl + productGetByIdUrl + productId).header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, res ->
                        Mono.error(new WebClientResponseException(
                                res.statusCode().value(),
                                "ProductId not found",
                                res.headers().asHttpHeaders(),
                                null,
                                null
                        ))
                )
                .bodyToMono(String.class)
                .block();
        if (response == null || response.isBlank()) {
            throw new EcommException(HttpStatus.NOT_FOUND, "Product not found");
        }

    }


}