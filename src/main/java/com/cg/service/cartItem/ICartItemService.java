package com.cg.service.cartItem;

import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartItemService extends IGeneralService<CartItem> {
    Optional<CartItemDTO> getCartInfoDTOByUserDTO(Long id);

    List<CartItemDTO> getAllCartItemByCartId(Long id);

    List<CartItemDTO> getCartItemByCustomerEmail(String userName);

    Optional<CartItemDTO> getCartItemByCartItemId(Long id);
}
