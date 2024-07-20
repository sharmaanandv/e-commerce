package org.ecomm.product.controller;

import org.ecomm.product.config.RoleRequired;
import org.ecomm.product.domain.CreateProduct;
import org.ecomm.product.domain.Product;
import org.ecomm.product.domain.UpdateProduct;
import org.ecomm.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RoleRequired("ROLE_USER")
    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @RoleRequired("ROLE_USER")
    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productService.findById(id);
    }

    @RoleRequired("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProduct createProduct) {
        Product product = productService.createProduct(createProduct);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @RoleRequired("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody UpdateProduct updateProduct) {
        updateProduct.setId(id);
        Product product = productService.updateProduct(updateProduct);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RoleRequired("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

}
