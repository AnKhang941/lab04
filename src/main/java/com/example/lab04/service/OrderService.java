package com.example.lab04.service;

import com.example.lab04.model.*;
import com.example.lab04.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order checkout(List<CartItem> cartItems) {
        // Tạo Order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(
                cartItems.stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum()
        );
        orderRepository.save(order);

        // Lưu từng OrderDetail
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getId().intValue()).orElse(null);
            if (product == null) continue;

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            orderDetailRepository.save(detail);
        }

        return order;
    }
}