package com.danpopescu.shop.payload;

import com.danpopescu.shop.model.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Component
public class ProductRepresentations {

    public Product from(ProductInputDto payload) {
        return Product.builder()
                .title(payload.title)
                .description(payload.description)
                .price(payload.price)
                .build();
    }

    public Product from(ProductInputDto payload, Product existing) {
        existing.setTitle(payload.title);
        existing.setDescription(payload.description);
        existing.setPrice(payload.price);
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

    @Data
    public static class ProductOutputDto {
        private String id, title, description;
        private BigDecimal price;
    }

    @Data
    public static class ProductInputDto {
        private @NotBlank String title, description;
        private @NotNull @Min(0) BigDecimal price;
    }
}
