package com.danpopescu.shop.web.mapper;

import com.danpopescu.shop.domain.Product;
import com.danpopescu.shop.web.payload.product.ProductInputDto;
import com.danpopescu.shop.web.payload.product.ProductOutputDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product from(ProductInputDto payload) {
        return Product.builder()
                .title(payload.getTitle())
                .description(payload.getDescription())
                .price(payload.getPrice())
                .build();
    }

    public Product from(ProductInputDto payload, Product existing) {
        existing.setTitle(payload.getTitle());
        existing.setDescription(payload.getDescription());
        existing.setPrice(payload.getPrice());
        return existing;
    }

    public ProductOutputDto toDto(Product product) {
        var dto = new ProductOutputDto();
        dto.setId(product.getId().toString());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        return dto;
    }

}
