package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.cart.ICartService;
import com.cg.service.cartItem.ICartItemService;
import com.cg.service.role.RoleService;
import com.cg.service.user.IUserService;
import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {
    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;


    @GetMapping("/{cartId}")
    //cart Item
    public ResponseEntity<?> getCarItemByCartId(@PathVariable Long cartId) {
        try {
            List<CartItemDTO> cartList = cartItemService.getAllCartItemByCartId(cartId);
            return new ResponseEntity<>(cartList,HttpStatus.OK);

        }catch (Exception e){
            throw new DataInputException("Không có cart");
        }
    }

    @GetMapping("/cart-item/id/{id}")
    public ResponseEntity<?> getCarItemByCartItemId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cartItemService.getCartItemByCartItemId(id).get(),HttpStatus.OK);

        }catch (Exception e){
            throw new DataInputException("Không có cart");
        }
    }

    @GetMapping("/cart-item/{userName}")
    public ResponseEntity<?> getCartItemByCustomerUserName(@PathVariable String userName) {
        try {
            List<CartItemDTO> cartList = cartItemService.getCartItemByCustomerEmail(userName);
            return new ResponseEntity<>(cartList,HttpStatus.OK);

        }catch (Exception e){
            throw new DataInputException("Không có cart");
        }
    }

    @PostMapping("/{idProduct}/{quantity}")
    public ResponseEntity<?> doCreateCart(@RequestBody UserDTO userDTO, @PathVariable Long idProduct,@PathVariable int quantity,BindingResult bindingResult) {
        try {
            Cart cart = cartService.saveCartAndCartItem(userDTO,idProduct,quantity);
            return new ResponseEntity<>(cart.toCartInfoDTO(),HttpStatus.CREATED);
        }catch (Exception e){
           throw new DataInputException("Fail");
        }
    }

    @PutMapping("/cart-item-change-input")
    public ResponseEntity<?> doChangeTotal(@RequestBody CartItemDTO cartItemDTO,BindingResult bindingResult) {
        try {
            Product product = cartItemDTO.getProduct().toProduct();

            CartItem cartItem = cartItemDTO.toCartItem();
            BigDecimal newPrice =  product.getPrice();
            long quantity = cartItem.getQuantity();
            BigDecimal newAmount = newPrice.multiply(new BigDecimal(quantity));
            cartItem.setPrice(newPrice);
            cartItem.setTotalPrice(newAmount);
            CartItem cart = cartItemService.save(cartItem);
            return new ResponseEntity<>(cart.toCartItemDTO(),HttpStatus.ACCEPTED);
        }catch (Exception e){
            throw new DataInputException("Fail");
        }
    }

    @DeleteMapping("/cart-item/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        try {
            cartItemService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            throw new DataInputException("Xóa cart thất bại!");
        }
    }

}
