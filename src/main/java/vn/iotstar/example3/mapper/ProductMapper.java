package vn.iotstar.example3.mapper;

import org.mapstruct.*;
import vn.iotstar.example3.dto.ProductDTO;
import vn.iotstar.example3.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "ownerName", source = "user.username")
    ProductDTO toDto(Product product);

    @Mapping(target = "user", ignore = true)
    Product toEntity(ProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    void updateEntity(ProductDTO dto, @MappingTarget Product product);
}
