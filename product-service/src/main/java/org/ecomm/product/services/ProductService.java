package org.ecomm.product.services;


import com.ecomm.comms.exception.EcommException;
import org.ecomm.product.domain.CreateProduct;
import org.ecomm.product.domain.Product;
import org.ecomm.product.domain.UpdateProduct;
import org.ecomm.product.entity.ProductEntity;
import org.ecomm.product.mapper.ProductMappers;
import org.ecomm.product.repo.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductMappers productMappers;
    private final ProductRepository productRepository;

    public ProductService(ProductMappers productMappers, ProductRepository productRepository) {
        this.productMappers = productMappers;
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll().stream().map(productMappers::toProduct).collect(Collectors.toList());
    }

    public Product findById(Long id) {
        return productRepository.findById(id).map(productMappers::toProduct).orElse(null);
    }

    public Product createProduct(CreateProduct product) {
        ProductEntity entity = productMappers.toEntity(product);
        entity.setVersion(1L);
        return productMappers.toProduct(productRepository.save(entity));
    }

    @Transactional
    public Product updateProduct(UpdateProduct updateProduct) {
        Optional<ProductEntity> productEntity = productRepository.findById(updateProduct.getId());
        if (productEntity.isPresent()) {
            productEntity.get().setName(updateProduct.getName());
            productEntity.get().setPrice(updateProduct.getPrice());
            productEntity.get().setStock(updateProduct.getStock());
            ProductEntity product = productRepository.save(productEntity.get());
            return productMappers.toProduct(product);
        }
        throw new EcommException("Product not found");
    }

    public void deleteById(Long id) {
        // check with order services if product is in use
        // Use soft delete instead, isDeleted = true
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        if (productEntity.isPresent()) {
            productEntity.get().setDeleted(true);
            productRepository.save(productEntity.get());
        } else {
            throw new EcommException("Product not found");
        }
    }
}