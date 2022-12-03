package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT NEW com.cg.model.dto.CartInfoDTO (" +
            "c.id, " +
            "c.grandTotal, " +
            "c.user " +
            ") " +
            "FROM Cart c " +
            "WHERE c.deleted = false And c.user.username = ?1 ")
    Optional<CartInfoDTO> getCartInfoDTOByUserDTO(String username);


    @Query("SELECT NEW com.cg.model.dto.CartInfoDTO (" +
            "c.id, " +
            "c.grandTotal, " +
            "c.user " +
            ") " +
            "FROM Cart c " +
            "WHERE c.user.id = ?1 ")
    Optional<CartInfoDTO> getCartInfoDTOByUserId(Long id);

    Optional<Cart> getCartByUser(User user);

}
