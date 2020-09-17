package com.danpopescu.shop.web.payload.order;

import com.danpopescu.shop.service.ProductService;
import lombok.Data;
import org.springframework.validation.Errors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class OrderInputDto {
    private @NotNull UUID pid;
    private @NotNull @Min(1) Integer count;
    private @NotBlank String comment;

    public void validate(Errors errors, ProductService productService) {
        if (!productService.existsById(this.pid)) {
            errors.rejectValue("pid", "InvalidProductId");
        }
    }
}
