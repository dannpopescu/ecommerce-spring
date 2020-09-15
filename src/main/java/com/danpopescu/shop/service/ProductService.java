package com.danpopescu.shop.service;

import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.payload.CreateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product createProduct(CreateProductRequest productRequest);

    List<Product> getAll();

    Product getById(UUID id);

    void deleteById(UUID id);

    Product save(Product product);
}
