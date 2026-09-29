package vn.iotstar.example3.repository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.example3.entity.Product;
import vn.iotstar.example3.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByUserAndNameContainingIgnoreCase(User user, String keyword, Pageable pageable);
    long countByUser(User user);
}
