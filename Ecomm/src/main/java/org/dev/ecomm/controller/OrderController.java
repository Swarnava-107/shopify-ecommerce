package org.dev.ecomm.controller;

import org.dev.ecomm.dto.OrderDto;
import org.dev.ecomm.model.OrderRequest;
import org.dev.ecomm.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    private OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{userId}")
    public OrderDto placeOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(id, orderRequest.getProductQuantities(),orderRequest.getTotalPrice());
    }

    @GetMapping("/all-orders")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrderByUser(userId);
    }
}
