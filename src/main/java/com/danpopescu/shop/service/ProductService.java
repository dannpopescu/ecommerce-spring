package com.danpopescu.shop.service;

import com.danpopescu.shop.exception.ResourceNotFoundException;
import com.danpopescu.shop.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> findAll();

    Product findById(UUID id) throws ResourceNotFoundException;

    void deleteById(UUID id) throws ResourceNotFoundException;

    Product save(Product product);

    boolean existsById(UUID id);
}
