package ait.project.deutscheecke.product.dao;


import ait.project.deutscheecke.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findProductsByCategoryId(Long categoryId);
    List<Product> findProductsByPriceBetween(double minPrice, double maxPrice);
    List<Product> findProductsByTitleContainsIgnoreCase(String title);


    // Новый метод для поиска всех продуктов, отсортированных по названию
    @Query("SELECT p FROM Product p ORDER BY p.title ASC")
    Iterable<Product> findAllOrderByTitleAsc();

    @Query("SELECT p FROM Product p ORDER BY p.title DESC")
    Iterable<Product> findAllOrderByTitleDesc();

    // Новый метод для поиска всех продуктов, отсортированных по цене
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    Iterable<Product> findAllOrderByPriceAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    Iterable<Product> findAllOrderByPriceDesc();
}
