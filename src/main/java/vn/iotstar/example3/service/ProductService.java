package vn.iotstar.example3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.iotstar.example3.dto.ProductDTO;
import vn.iotstar.example3.entity.*;
import vn.iotstar.example3.mapper.ProductMapper;
import vn.iotstar.example3.repository.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper mapper;

    public Page<ProductDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> result = (keyword == null || keyword.isBlank())
                ? productRepository.findAll(pageable)
                : productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return result.map(mapper::toDto);
    }

    public Page<ProductDTO> searchByOwner(String login, String keyword, int page, int size) {
        User owner = userRepository.findByUsernameOrEmail(login, login).orElseThrow();
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findByUserAndNameContainingIgnoreCase(owner,
                keyword == null ? "" : keyword, pageable).map(mapper::toDto);
    }

    public ProductDTO find(Long id) { return mapper.toDto(productRepository.findById(id).orElseThrow()); }

    public void save(ProductDTO dto, String login, String imageUrl) {
        User owner = userRepository.findByUsernameOrEmail(login, login).orElseThrow();
        Product p = dto.getId() == null ? new Product() : productRepository.findById(dto.getId()).orElseThrow();
        mapper.updateEntity(dto, p);
        p.setUser(owner);
        if (imageUrl != null) p.setImages(imageUrl);
        productRepository.save(p);
    }

    public void delete(Long id) { productRepository.deleteById(id); }
    public long count() { return productRepository.count(); }
    public long countByLogin(String login) {
        User owner = userRepository.findByUsernameOrEmail(login, login).orElseThrow();
        return productRepository.countByUser(owner);
    }
}
