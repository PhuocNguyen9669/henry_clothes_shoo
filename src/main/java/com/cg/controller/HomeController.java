package com.cg.controller;

import com.cg.model.dto.CartInfoDTO;
import com.cg.model.dto.CartItemDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.cart.ICartService;
import com.cg.service.cartItem.ICartItemService;
import com.cg.service.order.OrderService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ICartItemService cartItemService;

    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }


    @GetMapping("/home")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/home-dashboard/home");
        String email = getPrincipal();
        modelAndView.addObject("User", email);


        return modelAndView;
    }

    @GetMapping("/products")
    public ModelAndView showProductPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product/list");
        String email = getPrincipal();
        modelAndView.addObject("User", email);

        return modelAndView;
    }

    @GetMapping("/orderItems")
    public ModelAndView showOrderItemPage(@ModelAttribute OrderDTO orderDTO) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/orderItem/index");
        List<OrderDTO> orderDTOS = orderService.findOrderDTO();

        String email = getPrincipal();
        modelAndView.addObject("orderDTOS", orderDTOS);
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogin() {
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByUsername(email);
        if (userDTOOptional.isPresent()) {
            return "redirect:/home";
        }
        return "/login";
    }

    // CustomerPage

    @GetMapping("/customer-view")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ModelAndView showCustomerView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/index");
        String email = getPrincipal();
        modelAndView.addObject("User", email);
        try {
            Optional<CartInfoDTO> cartInfoDTO = cartService.getCartInfoDTOByUserDTO(email);
            List<CartItemDTO> cartItemDTOList = cartItemService.getAllCartItemByCartId(cartInfoDTO.get().getId());
            int sum = 0;
            for (CartItemDTO cartItemDTO : cartItemDTOList) {
                sum += cartItemDTO.getQuantity();
            }
            modelAndView.addObject("AmoutCartItem", sum);
            return modelAndView;
        } catch (Exception e) {
            int sum = 0;
            modelAndView.addObject("AmoutCartItem", sum);
            return modelAndView;
        }

    }

    @GetMapping("/customer-view/order")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ModelAndView showOrderCustomerView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customer/orderCustomer/order");
        String email = getPrincipal();
        modelAndView.addObject("User", email);
        try {
            Optional<CartInfoDTO> cartInfoDTO = cartService.getCartInfoDTOByUserDTO(email);
            List<CartItemDTO> cartItemDTOList = cartItemService.getAllCartItemByCartId(cartInfoDTO.get().getId());
            int sum = 0;
            for (CartItemDTO cartItemDTO : cartItemDTOList) {
                sum += cartItemDTO.getQuantity();
            }
            modelAndView.addObject("AmoutCartItem", sum);
            return modelAndView;
        } catch (Exception e) {
            int sum = 0;
            modelAndView.addObject("AmoutCartItem", sum);
            return modelAndView;
        }
    }
}
