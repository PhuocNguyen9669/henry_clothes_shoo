package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<ProductDTO> getProductDTOById(Long id);


    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title," +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category) " +
            "FROM Product AS p  " +
            "WHERE p.deleted = false AND p.id = ?1 ")
    Optional<ProductDTO> findByProductDTOId(Long id);

    Boolean existsByName(String name);

    //    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
//            "p.id, " +
//            "p.title, " +
//            "p.price, " +
//            "p.quantity, " +
//            "p.urlImage, " +
//            "p.category) " +
//            "FROM Product AS p " +
//            "JOIN Category AS c " +
//            "ON c.id = p.category.id " +
//            "WHERE p.deleted= false AND CONCAT(" +
//            "p.id, " +
//            "p.title, " +
//            "p.quantity, " +
//            "p.price, " +
//            "p.urlImage, " +
//            "c.title) LIKE ?1 ")
    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category ) " +
            "FROM Product AS p " +
            "WHERE p.deleted= false  " +
            "And ( p.title Like ?1 " +
            "or p.category.title like ?1) ")
    List<ProductDTO> searchAllByProduct(String keyword);

    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title," +
            "p.price, " +
            "p.quantity, " +
            "p.urlImage, " +
            "p.category) " +
            "FROM Product AS p  " +
            "WHERE p.deleted = false")
    List<ProductDTO> findAllProductDTO();

}
