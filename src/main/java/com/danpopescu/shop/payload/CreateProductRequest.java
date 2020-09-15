package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Min(0)
    @NotNull
    private BigDecimal price;
}
