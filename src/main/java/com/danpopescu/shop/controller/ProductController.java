package com.danpopescu.shop.controller;

import com.danpopescu.shop.model.Product;
import com.danpopescu.shop.payload.ProductRepresentations;
import com.danpopescu.shop.payload.ProductRepresentations.ProductInputDto;
import com.danpopescu.shop.payload.ProductRepresentations.ProductOutputDto;
import com.danpopescu.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepresentations representations;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductInputDto payload) {

        Product product = productService.save(representations.from(payload));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(location).body(representations.toDto(product));
    }

    @GetMapping
    public List<ProductOutputDto> getProducts() {
        return productService.findAll().stream()
                .map(representations::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductOutputDto getProduct(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return representations.toDto(product);
    }

    @PutMapping("/{id}")
    public ProductOutputDto updateProduct(@PathVariable UUID id,
                                          @Valid @RequestBody ProductInputDto payload) {

        Product existing = productService.findById(id);
        Product updated = productService.save(representations.from(payload, existing));

        return representations.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
