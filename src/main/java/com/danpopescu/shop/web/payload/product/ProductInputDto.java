package com.danpopescu.shop.web.payload.product;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductInputDto {
    private @NotBlank String title, description;
    private @NotNull @Min(0) BigDecimal price;
}
