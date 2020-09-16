package com.danpopescu.shop.util;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.payload.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserSummary userToUserSummary(Account account) {
        return new UserSummary(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getUsername()
        );
    }

    public UserProfile userToUserProfile(Account account) {
        return new UserProfile(
                account.getId(),
                account.getFirstName(),
                account.getFirstName(),
                account.getEmail(),
                account.getUsername(),
                account.getAddress()
        );
    }

    public OrderSummary orderToOrderSummary(Order order) {
        return new OrderSummary(
                order.getId(),
                order.getAccount().getId(),
                order.getProduct().getId(),
                order.getCount(),
                order.getComment()
        );
    }

    public OrderResponse orderToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                userToUserProfile(order.getAccount()),
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
