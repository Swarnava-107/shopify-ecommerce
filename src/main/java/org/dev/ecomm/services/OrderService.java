package org.dev.ecomm.services;

import org.dev.ecomm.dto.OrderDto;
import org.dev.ecomm.repository.OrderRepository;
import org.dev.ecomm.repository.ProductRepository;
import org.dev.ecomm.repository.UserRepository;
import org.springframework.stereotype.Service;

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


    public OrderDto placeOrder()
}
