package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductDTO implements Validator {
    private String id;

    @NotEmpty(message = "Tên sản phẩm không được để trống!")
    @Length(min = 5, message = "Tên sản phẩm có ít nhất 5 ký tự")
    @Length(max = 100, message = "Tên sản phẩm có nhiều nhất 100 ký tự")
    private String title;

    @NotNull(message = "Vui lòng nhập số tiền sản phẩm!")
    @Min(value = 10000, message = "Số tiền nhỏ nhất là 10,000VND")
    @Max(value = 999999999, message = "Số tiền tối đa là 999,999,999VND")
    private BigDecimal price;

    @NotNull(message = "Số lượng sản phẩm không được để trống!")
    @Min(value = 1, message = "Số lượng sản phẩm nhỏ nhất là 1!")
    @Max(value = 999, message = "Số lượng sản phẩm lớn nhất là 999!")
    private int quantity;


    @NotBlank(message = "Vui lòng nhập link ảnh sản phẩm!")
    private String urlImage;

    @Valid
    @NotNull(message = "Vui lòng nhập danh mục sản phẩm!")
    private CategoryDTO category;

    public Product toProduct() {
        return new Product()
                .setId(Long.parseLong(id))
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setUrlImage(urlImage)
                .setCategory(category.toCategory());
    }

    public ProductDTO(Long id, String title, BigDecimal price, Integer quantity, String urlImage, Category category) {
        this.id = id.toString();
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.urlImage = urlImage;
        this.category = category.toCategoryDTO();
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
