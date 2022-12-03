package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGeneralService<Product> {

    Boolean existsByName(String name);

    Optional<ProductDTO> getProductDTOById(Long id);


    Optional<ProductDTO>findByProductDTOId(Long id);

    List<ProductDTO> searchAllByProduct(String keyword);

    List<ProductDTO> findAllProductDTO();


}
