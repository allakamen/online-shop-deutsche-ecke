package ait.project.deutscheecke.product.service;


import ait.project.deutscheecke.product.dto.CategoryDto;
import ait.project.deutscheecke.product.dto.ProductDto;

public interface ProductService {
    boolean addProduct(ProductDto productDto);
    ProductDto removeProduct(Long id);
    ProductDto increaseProductQuantity(Long productId, int quantityToAdd);
    ProductDto decreaseProductQuantity(Long productId, int quantityToSubtract);
    boolean addCategory(CategoryDto categoryDto);
    CategoryDto removeCategory(Long id);
    ProductDto findProductById(Long id);
    Iterable<ProductDto> findByCategory(long categoryId);
    Iterable<ProductDto> findByPriceRange(double minPrice, double maxPrice);
    Iterable<ProductDto> findByTitle(String title);

    Iterable<ProductDto> findAllOrderByTitleAsc();
    Iterable<ProductDto> findAllOrderByTitleDesc();
    Iterable<ProductDto> findAllOrderByPriceAsc();
    Iterable<ProductDto> findAllOrderByPriceDesc();
}
