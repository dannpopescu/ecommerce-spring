package com.danpopescu.shop.service;

import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.payload.CreateOrderRequest;
import com.danpopescu.shop.security.UserPrincipal;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(UserPrincipal userPrincipal, CreateOrderRequest orderRequest);

    List<Order> getAll();

    Order getById(UUID id);

    void deleteById(UUID id);

    Order save(Order order);
}
