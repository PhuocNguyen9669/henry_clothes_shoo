package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Customer;
import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.SearchDTO;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getListProduct() {
        List<ProductDTO> productDTOS = productService.findAllProductDTO();
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/product-view")
    public ResponseEntity<?> getListProductIndex() {
        try {
            return new ResponseEntity<>(productService.findAllProductDTO(), HttpStatus.OK);
        }catch (Exception e){
            throw new DataInputException("không có product");
        }
    }


//    @GetMapping("/category")
//    public ResponseEntity<?> getCategory() {
//
//        List<CategoryDTO> categoryDTOS = categoryService.findAllCategoryDTO();
//
//        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable String id){
//        Optional<ProductDTO> optionalProductDTO = null;
//
//        try {
//            optionalProductDTO = productService.getProductDTOById(Long.parseLong(id));
//        } catch (NumberFormatException e) {
//            throw new ResourceNotFoundException("ID không hợp lệ!!");
//        }
//
//        if (!optionalProductDTO.isPresent()) {
//            return new ResponseEntity<>("Sản phẩm không tồn tại!", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(optionalProductDTO.get(), HttpStatus.OK);
//    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreateProduct(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Boolean existsByName = productService.existsByName(productDTO.getTitle());

        if (existsByName) {
            return new ResponseEntity<>("Tên sản phẩm đã tồn tại!", HttpStatus.NOT_FOUND);
        }
        productDTO.setId(String.valueOf(0L));

        Optional<Category> category = categoryService.findById(productDTO.getCategory().toCategory().getId());

        if (!category.isPresent()){
            return new ResponseEntity<>("Danh mục sản phẩm không tồn tại!", HttpStatus.NOT_FOUND);
        }

        try {
           Product newProduct = productService.save(productDTO.toProduct());

            return new ResponseEntity<>(newProduct.toProductDTO(), HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Thông tin không hợp lệ, vui lòng kiểm tra lại!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        Product product = productOptional.get();

        ProductDTO productDTO = product.toProductDTO();

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }


    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doUpdate (@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return appUtil.mapErrorToResponse(bindingResult);
        }

        Product updateProduct = productService.save(productDTO.toProduct());

        return new ResponseEntity<>(updateProduct.toProductDTO(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doDelete(@PathVariable Long productId) {
        Optional<Product> product = productService.findById(productId);
        if (product.isPresent()) {

            productService.softDelete(product.get());
            return new ResponseEntity<>("Delete product success", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Delete product error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> getSearchProductDTO(@PathVariable String keyword ) {

        String Strkeyword = "%"+keyword+"%";
        List<ProductDTO> productDTOS = productService.searchAllByProduct(Strkeyword);

        if (productDTOS.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm!!!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

}
