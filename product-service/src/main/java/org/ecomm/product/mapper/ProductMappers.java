package org.ecomm.product.mapper;

import org.ecomm.product.domain.CreateProduct;
import org.ecomm.product.domain.Product;
import org.ecomm.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMappers {

    ProductMappers INSTANCE = Mappers.getMapper(ProductMappers.class);

    Product toProduct(ProductEntity entity);

    ProductEntity toEntity(CreateProduct createProduct);

}