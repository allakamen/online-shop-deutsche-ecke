package ait.project.deutscheecke.product.dao;


import ait.project.deutscheecke.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    //TODO исправить categoryId при возврате
    List<Product> findProductsByCategoryId(Long categoryId);
    List<Product> findProductsByPriceBetween(double minPrice, double maxPrice);
    List<Product> findProductsByTitleContainsIgnoreCase(String title);
}
