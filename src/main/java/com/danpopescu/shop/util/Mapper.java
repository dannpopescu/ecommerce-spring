package com.danpopescu.shop.util;

import com.danpopescu.shop.model.Account;
import com.danpopescu.shop.model.Order;
import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.payload.OrderResponse;
import com.danpopescu.shop.payload.OrderSummary;
import com.danpopescu.shop.payload.ProductResponse;
import com.danpopescu.shop.payload.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

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
