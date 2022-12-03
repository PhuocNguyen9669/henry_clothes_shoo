package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartInfoDTO {
    private Long id;

    private BigDecimal grandTotal;

    private UserDTO user;

    public CartInfoDTO(Long id, BigDecimal grandTotal,User user) {
        this.id = id;
        this.grandTotal = grandTotal;
        this.user = user.toUserDTO();
    }

    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setGrandTotal(grandTotal)
                .setUser(user.toUser());
    }
}
