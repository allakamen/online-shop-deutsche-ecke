package ait.project.deutscheecke.product.service;


import ait.project.deutscheecke.product.dao.CategoryRepository;
import ait.project.deutscheecke.product.dao.ProductRepository;
import ait.project.deutscheecke.product.dto.CategoryDto;
import ait.project.deutscheecke.product.dto.ProductDto;
import ait.project.deutscheecke.product.dto.exceptions.EntityNotFoundException;
import ait.project.deutscheecke.product.model.Category;
import ait.project.deutscheecke.product.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;



import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.stream.Collectors;

import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    final ModelMapper modelMapper;


    @Transactional
    @Override
    public boolean addProduct(ProductDto productDto) {
        if (productRepository.findById(productDto.getId()).isPresent()) {
            return false;
        }
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(EntityNotFoundException::new);
        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        productRepository.save(product);
        return true;
    }

    @Transactional
    @Override
    public ProductDto removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        productRepository.delete(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto increaseProductQuantity(Long productId, int quantityToAdd) {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        int currentQuantity = product.getQuantity();
        product.setQuantity(currentQuantity + quantityToAdd);
        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto decreaseProductQuantity(Long productId, int quantityToSubtract) {
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        int currentQuantity = product.getQuantity();
        if (currentQuantity < quantityToSubtract || currentQuantity == 0) {
            throw new EntityNotFoundException();
        }
        product.setQuantity(currentQuantity - quantityToSubtract);
        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Transactional
    @Override
    public boolean addCategory(CategoryDto categoryDto) {
        if (categoryRepository.findById(categoryDto.getId()).isPresent()) {
            return false;
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepository.save(category);
        return true;
    }

    @Transactional
    @Override
    public CategoryDto removeCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public ProductDto findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setCategoryId(product.getCategory().getId());
            return productDto;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Iterable<ProductDto> findByCategory(long categoryId) {
        Iterable<Product> products = productRepository.findProductsByCategoryId(categoryId);
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategoryId(product.getCategory().getId());
                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<ProductDto> findByPriceRange(double minPrice, double maxPrice) {
        Iterable<Product> products = productRepository.findProductsByPriceBetween(minPrice, maxPrice);
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategoryId(product.getCategory().getId());
                    return productDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<ProductDto> findByTitle(String title) {
        title = title.replaceAll("\\r\\n|\\r|\\n", "");
        Iterable<Product> products = productRepository.findProductsByTitleContainsIgnoreCase(title);
        return StreamSupport.stream(products.spliterator(), false)
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategoryId(product.getCategory().getId());
                    return productDto;
                })
                .collect(Collectors.toList());
    }
}
