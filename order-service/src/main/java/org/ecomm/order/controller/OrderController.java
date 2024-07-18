package org.ecomm.order.controller;

import org.ecomm.order.domain.CreateOrder;
import org.ecomm.order.domain.Order;
import org.ecomm.order.domain.UpdateOrder;
import org.ecomm.order.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> list() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrder createOrder) {
        Order order = orderService.createOrder(createOrder);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Orders are immutable
    /*@PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody UpdateOrder updateOrder) {
        updateOrder.setId(id);
        Order order = orderService.updateOrder(updateOrder);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }*/

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

}