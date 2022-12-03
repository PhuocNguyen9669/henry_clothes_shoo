package com.cg.repository;

import com.cg.model.CartItem;
import com.cg.model.User;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT NEW com.cg.model.dto.CartItemDTO (" +
            "c.id, " +
            "c.title, " +
            "c.price, " +
            "c.quantity," +
            "c.totalPrice, " +
            "c.product," +
            "c.cart " +
            ") " +
            "FROM CartItem c " +
            "WHERE  c.cart.id = ?1 ")
    Optional<CartItemDTO> getCartInfoDTOByUserDTO(Long id);

    @Query("SELECT NEW com.cg.model.dto.CartItemDTO (" +
            "c.id, " +
            "c.title, " +
            "c.price, " +
            "c.quantity," +
            "c.totalPrice, " +
            "c.product," +
            "c.cart " +
            ") " +
            "FROM CartItem c " +
            "WHERE  c.cart.id = ?1 " +
            "And c.product.id = ?2")
    Optional<CartItemDTO> getCartInfoDTOByUserDTOAndProductName(Long id, Long productId);

    @Query("SELECT NEW com.cg.model.dto.CartItemDTO (" +
            "c.id, " +
            "c.title, " +
            "c.price, " +
            "c.quantity," +
            "c.totalPrice, " +
            "c.product," +
            "c.cart " +
            ") " +
            "FROM CartItem c " +
            "WHERE  c.cart.id = ?1 ")
    List<CartItemDTO> getAllCartItemByCartId(Long id);

    @Query("SELECT NEW com.cg.model.dto.CartItemDTO (" +
            "c.id, " +
            "c.title, " +
            "c.price, " +
            "c.quantity," +
            "c.totalPrice, " +
            "c.product," +
            "c.cart " +
            ") " +
            "FROM CartItem c " +
            "WHERE  c.cart.user.username = ?1 ")
    List<CartItemDTO> getCartItemByCustomerEmail(String userName);

    @Query("SELECT NEW com.cg.model.dto.CartItemDTO (" +
            "c.id, " +
            "c.title, " +
            "c.price, " +
            "c.quantity," +
            "c.totalPrice, " +
            "c.product," +
            "c.cart " +
            ") " +
            "FROM CartItem c " +
            "WHERE  c.id = ?1 ")
    Optional<CartItemDTO> getCartItemByCartItemId(Long id);
}

