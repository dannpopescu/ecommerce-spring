package com.danpopescu.shop.service;

import com.danpopescu.shop.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> findAll();

    Order findById(UUID id);

    void deleteById(UUID id);

    Order save(Order order);
}
