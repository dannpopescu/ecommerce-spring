package com.danpopescu.shop.util;

import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.model.User;
import com.danpopescu.shop.payload.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserSummary userToUserSummary(User user) {
        return new UserSummary(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername()
        );
    }

    public UserProfile userToUserProfile(User user) {
        return new UserProfile(
                user.getId(),
                user.getFirstName(),
                user.getFirstName(),
                user.getEmail(),
                user.getUsername(),
                user.getAddress()
        );
    }

    public OrderSummary orderToOrderSummary(Order order) {
        return new OrderSummary(
                order.getId(),
                order.getUser().getId(),
                order.getProduct().getId(),
                order.getCount(),
                order.getComment()
        );
    }

    public OrderResponse orderToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                userToUserProfile(order.getUser()),
                productToProductResponse(order.getProduct()),
                order.getCount(),
                order.getComment(),
                order.getCreatedAt(),
                order.getModifiedAt()
        );
    }

    public ProductResponse productToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
