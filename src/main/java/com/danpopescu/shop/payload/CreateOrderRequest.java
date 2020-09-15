package com.danpopescu.shop.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull
    private UUID pid;

    @NotNull
    @Min(1)
    private Integer count;

    @NotBlank
    private String comment;
}
