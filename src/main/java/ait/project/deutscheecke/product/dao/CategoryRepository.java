package ait.project.deutscheecke.product.dao;


import ait.project.deutscheecke.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
