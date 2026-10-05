package org.dev.ecomm.services;

import org.dev.ecomm.dto.OrderDto;
import org.dev.ecomm.dto.OrderItemDto;
import org.dev.ecomm.model.Order;
import org.dev.ecomm.model.OrderItem;
import org.dev.ecomm.model.Product;
import org.dev.ecomm.model.User;
import org.dev.ecomm.repository.OrderRepository;
import org.dev.ecomm.repository.ProductRepository;
import org.dev.ecomm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    // place order for a particular user ---->
    public OrderDto placeOrder(Long userId, Map<Long, Integer> productQuantities, Double totalPrice ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Pending");
        order.setOrderDate(new Date());
        order.setTotalPrice(totalPrice);

        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());

            orderItems.add(orderItem);
            orderItemDtos.add(new OrderItemDto(product.getName(),product.getPrice(),entry.getValue()));
        }

        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return new OrderDto(savedOrder.getId(), savedOrder.getTotalPrice(), savedOrder.getStatus(),
                            savedOrder.getOrderDate(), savedOrder.getUser().getName(), user.getEmail(), orderItemDtos);

    }


    // get all orders ---->
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAllOrderWithUsers();

        return orders.stream()
                .map(this :: convertToDto).collect(Collectors.toList());
    }


    // get all orders of a user ---->
    public List<OrderDto> getOrderByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        List<Order> orders = orderRepository.findAllByUser(user.get());
        return orders.stream()
                .map(this :: convertToDto).collect(Collectors.toList());
    }



    private OrderDto convertToDto(Order order) {
        List<OrderItemDto> orderItems = order.getOrderItems().stream()
                .map(items -> new OrderItemDto(
                        items.getProduct().getName(),
                        items.getProduct().getPrice(),
                        items.getQuantity()
                )).collect(Collectors.toList());

        return new OrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getOrderDate(),
                order.getUser() != null ? order.getUser().getName() : "Unknown",
                order.getUser() != null ? order.getUser().getEmail() : "Unknown",
                orderItems
        );
    }
}
