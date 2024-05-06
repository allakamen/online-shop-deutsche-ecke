package ait.project.deutscheecke.product.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private int quantity;
    private Long categoryId;
    private List<String> images;
}
