package ait.project.deutscheecke.product.controller;


import ait.project.deutscheecke.product.dto.CategoryDto;
import ait.project.deutscheecke.product.dto.ProductDto;
import ait.project.deutscheecke.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    final ProductService productService;


    @PostMapping("/product")
    public boolean addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @DeleteMapping("/product/{id}")
    public ProductDto removeProduct(@PathVariable Long id) {
        return productService.removeProduct(id);
    }

    @PostMapping("/category")
    public boolean addCategory(@RequestBody CategoryDto categoryDto) {
        return productService.addCategory(categoryDto);
    }

    @DeleteMapping("/category/{id}")
    public CategoryDto removeCategory(@PathVariable Long id) {
        return productService.removeCategory(id);
    }

    @GetMapping("/products/search/{productId}")
    public ProductDto findProductById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    @GetMapping("/products/search/category/{categoryId}")
    public Iterable<ProductDto> findByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/products/search/price/{minPrice}/{maxPrice}")
    public Iterable<ProductDto> findByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        return productService.findByPriceRange(minPrice, maxPrice);
    }


    @GetMapping("/products/search/title/{title}")
    public Iterable<ProductDto> findByTitle(@PathVariable String title) {
        System.out.println("title from postman: " + title);
        return productService.findByTitle(title);
    }

    @PostMapping("/products/{productId}/increase-quantity/{quantity}")
    public ProductDto increaseQuantity(@PathVariable Long productId, @PathVariable Integer quantity) {
        return productService.increaseProductQuantity(productId, quantity);
    }

    @PostMapping("/products/{productId}/decrease-quantity/{quantity}")
    public ProductDto decreaseQuantity(@PathVariable Long productId, @PathVariable Integer quantity) {
        return productService.decreaseProductQuantity(productId, quantity);
    }

    @GetMapping("/products/search")
    public Iterable<ProductDto> searchProducts(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {

        return switch (sortBy.toLowerCase()) {
            case "title" ->
                    order.equalsIgnoreCase("asc") ? productService.findAllOrderByTitleAsc() : productService.findAllOrderByTitleDesc();
            case "price" ->
                    order.equalsIgnoreCase("asc") ? productService.findAllOrderByPriceAsc() : productService.findAllOrderByPriceDesc();
            default -> throw new IllegalArgumentException("Invalid sortBy parameter");
        };
    }

}
