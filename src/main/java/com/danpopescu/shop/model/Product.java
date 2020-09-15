package com.danpopescu.shop.model;

import com.danpopescu.shop.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends DateAudit {

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    private String description;

    @Min(0)
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
}
