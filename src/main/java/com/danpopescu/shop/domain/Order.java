package com.danpopescu.shop.domain;

import com.danpopescu.shop.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends DateAudit {

    @NotNull
    @ManyToOne
    private Account customer;

    @NotNull
    @ManyToOne
    private Product product;

    @Min(1)
    private int count;

    @NotBlank
    private String comment;

}
