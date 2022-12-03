package com.cg.service.cartItem;

import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import com.cg.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements ICartItemService{
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CartItem getById(Long id) {
        return null;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void softDelete(CartItem cartItem) {

    }

    @Override
    public Optional<CartItemDTO> getCartInfoDTOByUserDTO(Long id) {
        return cartItemRepository.getCartInfoDTOByUserDTO(id);
    }

    @Override
    public List<CartItemDTO> getAllCartItemByCartId(Long id) {
        return cartItemRepository.getAllCartItemByCartId(id);
    }

    @Override
    public List<CartItemDTO> getCartItemByCustomerEmail(String userName) {
        return cartItemRepository.getCartItemByCustomerEmail(userName);
    }

    @Override
    public Optional<CartItemDTO> getCartItemByCartItemId(Long id) {
        return cartItemRepository.getCartItemByCartItemId(id);
    }
}
