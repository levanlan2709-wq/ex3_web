package vn.iotstar.example3.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.iotstar.example3.dto.ProductDTO;
import vn.iotstar.example3.entity.Product;
import vn.iotstar.example3.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-28T11:57:54+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260826-1225, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setUserId( productUserId( product ) );
        productDTO.setOwnerName( productUserUsername( product ) );
        productDTO.setDescription( product.getDescription() );
        productDTO.setId( product.getId() );
        productDTO.setImages( product.getImages() );
        productDTO.setName( product.getName() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setQuantity( product.getQuantity() );

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setDescription( dto.getDescription() );
        product.setId( dto.getId() );
        product.setImages( dto.getImages() );
        product.setName( dto.getName() );
        product.setPrice( dto.getPrice() );
        product.setQuantity( dto.getQuantity() );

        return product;
    }

    @Override
    public void updateEntity(ProductDTO dto, Product product) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            product.setDescription( dto.getDescription() );
        }
        if ( dto.getId() != null ) {
            product.setId( dto.getId() );
        }
        if ( dto.getImages() != null ) {
            product.setImages( dto.getImages() );
        }
        if ( dto.getName() != null ) {
            product.setName( dto.getName() );
        }
        if ( dto.getPrice() != null ) {
            product.setPrice( dto.getPrice() );
        }
        if ( dto.getQuantity() != null ) {
            product.setQuantity( dto.getQuantity() );
        }
    }

    private Long productUserId(Product product) {
        User user = product.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String productUserUsername(Product product) {
        User user = product.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }
}
