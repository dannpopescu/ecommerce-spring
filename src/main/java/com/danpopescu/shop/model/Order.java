package com.danpopescu.shop.model;

import com.danpopescu.shop.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

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
public class Order extends DateAudit {

    @NotNull
    @ManyToOne
    private Account account;

    @NotNull
    @ManyToOne
    private Product product;

    @Min(1)
    private int count;

    @NotBlank
    private String comment;

    public Order() {
    }

    public Order(Account account, Product product, int count, String comment) {
        this.account = account;
        this.product = product;
        this.count = count;
        this.comment = comment;
    }
}
