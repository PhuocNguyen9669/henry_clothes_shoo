package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(Cart cart) {

    }

    @Override
    @Transactional
    public Cart saveCartAndCartItem(UserDTO userDTO, Long idProduct, int quantity) {
        Optional<CartInfoDTO> optionalCartInfoDTO = cartRepository.getCartInfoDTOByUserDTO(userDTO.getUsername());
        if (optionalCartInfoDTO.isPresent()){
            Optional<CartItemDTO> cartItemDTO = cartItemRepository.getCartInfoDTOByUserDTOAndProductName(optionalCartInfoDTO.get().getId(),idProduct);
            if (cartItemDTO.isPresent()){
                cartItemDTO.get().setCart(optionalCartInfoDTO.get());
                ProductDTO productDTO = productRepository.findByProductDTOId(idProduct).get();
                cartItemDTO.get().setProduct(cartItemDTO.get().getProduct());
                cartItemDTO.get().setPrice(productDTO.toProduct().getPrice());
                cartItemDTO.get().setQuantity(cartItemDTO.get().getQuantity() + 1);
                cartItemDTO.get().setTotalPrice((productDTO.toProduct().getPrice().multiply(BigDecimal.valueOf(cartItemDTO.get().getQuantity()))));
                cartItemDTO.get().setTitle(productDTO.getTitle());
                cartItemRepository.save(cartItemDTO.get().toCartItem());
                optionalCartInfoDTO.get().setGrandTotal(optionalCartInfoDTO.get().getGrandTotal().add(cartItemDTO.get().getTotalPrice()));
                cartRepository.save(optionalCartInfoDTO.get().toCart());
                return optionalCartInfoDTO.get().toCart();
            }
            CartItem cartItem = new CartItem();
            cartItem.setCart(optionalCartInfoDTO.get().toCart());
            ProductDTO productDTO = productRepository.findByProductDTOId(idProduct).get();
            cartItem.setProduct(productDTO.toProduct());
            cartItem.setPrice(productDTO.toProduct().getPrice());
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(productDTO.toProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
            cartItem.setId(0L);
            cartItem.setTitle(productDTO.getTitle());
            cartItemRepository.save(cartItem);
            optionalCartInfoDTO.get().setGrandTotal(cartItem.getTotalPrice().add(optionalCartInfoDTO.get().getGrandTotal()));
            cartRepository.save(optionalCartInfoDTO.get().toCart());
            return optionalCartInfoDTO.get().toCart();
        }
        Cart cart = new Cart();
        cart.setId(0L);
        cart.setGrandTotal(BigDecimal.valueOf(0));
        Optional<UserDTO> userDTO1 = userRepository.findUserDTOFullByUsername(userDTO.getUsername());
        cart.setUser(userDTO1.get().toUser());
        Cart cart1 = cartRepository.save(cart);

        // tao cartItem
        CartItem cartItem = new CartItem();
        // tim cart vua tao
        cartItem.setCart(cart1);
        Optional<ProductDTO> productDTO = productRepository.findByProductDTOId(idProduct);
        cartItem.setProduct(productDTO.get().toProduct());
        cartItem.setPrice(productDTO.get().getPrice());
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(productDTO.get().getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItem.setId(0L);
        cartItem.setTitle(productDTO.get().getTitle());
        System.out.println(cartItem);
        cart1.setGrandTotal(cart1.getGrandTotal().add(cartItem.getTotalPrice()));
        cartRepository.save(cart1);
        cartItemRepository.save(cartItem);
        return cart1;

    }

    @Override
    public Optional<CartInfoDTO> getCartInfoDTOByUserDTO(String username) {
        return cartRepository.getCartInfoDTOByUserDTO(username);
    }

    @Override
    public Optional<CartInfoDTO> getCartInfoDTOByUserId(Long id) {
        return cartRepository.getCartInfoDTOByUserId(id);
    }
}
