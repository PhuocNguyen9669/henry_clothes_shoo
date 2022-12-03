package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {
    Cart saveCartAndCartItem(UserDTO userDTO, Long idProduct, int quantity);

    Optional<CartInfoDTO> getCartInfoDTOByUserDTO(String username);

    Optional<CartInfoDTO> getCartInfoDTOByUserId(Long id);

}
