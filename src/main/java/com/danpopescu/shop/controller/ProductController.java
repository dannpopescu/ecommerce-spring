package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.payload.CreateProductRequest;
import com.danpopescu.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
