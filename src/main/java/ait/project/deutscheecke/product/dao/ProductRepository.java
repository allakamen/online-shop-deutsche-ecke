package ait.project.deutscheecke.product.dao;


import ait.project.deutscheecke.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findProductsByCategoryId(Long categoryId);
    List<Product> findProductsByPriceBetween(double minPrice, double maxPrice);
    List<Product> findProductsByTitleContainsIgnoreCase(String title);
}
