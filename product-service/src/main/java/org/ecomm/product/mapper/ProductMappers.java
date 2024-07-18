package org.ecomm.product.mapper;

import org.ecomm.product.domain.CreateProduct;
import org.ecomm.product.domain.Product;
import org.ecomm.product.domain.UpdateProduct;
import org.ecomm.product.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMappers {

    ProductMappers INSTANCE = Mappers.getMapper(ProductMappers.class);

    //@Mapping(source = "id", target = "id")
    //@Mapping(source = "name", target = "name")
    Product toProduct(ProductEntity entity);

    ProductEntity toEntity(CreateProduct createProduct);

    ProductEntity toEntity(UpdateProduct updateProduct);
}