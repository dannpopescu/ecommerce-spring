package com.danpopescu.shop.service.impl;

import com.danpopescu.shop.exception.ResourceNotFoundException;
import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.model.User;
import com.danpopescu.shop.payload.CreateOrderRequest;
import com.danpopescu.shop.repository.OrderRepository;
import com.danpopescu.shop.repository.ProductRepository;
import com.danpopescu.shop.repository.UserRepository;
import com.danpopescu.shop.security.UserPrincipal;
import com.danpopescu.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Order createOrder(UserPrincipal userPrincipal, CreateOrderRequest orderRequest) {
        User customer = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        Product product = productRepository.findById(orderRequest.getPid())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", orderRequest.getPid()));

        Order order = new Order(customer, product, orderRequest.getCount(), orderRequest.getComment());

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }

    @Override
    public Order getById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    @Override
    public void deleteById(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", "id", id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
