package ar.com.thiagoianuzzi.stock.model.repository;

import ar.com.thiagoianuzzi.stock.model.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String words);
    Product findById(Long id);
    void deleteById(Long id);
}
