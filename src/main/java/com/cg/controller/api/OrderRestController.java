package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.service.cart.ICartService;
import com.cg.service.cartItem.ICartItemService;
import com.cg.service.oderItem.IOderItemService;
import com.cg.service.order.OrderService;
import com.cg.service.product.IProductService;
import com.cg.service.user.IUserService;
import com.cg.util.AppUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    OrderService orderService;

    @Autowired
    private IOderItemService oderItemService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private AppUtil appUtil;


    @PostMapping("/add")
    public ResponseEntity<?> doAddOrder(@RequestBody CustomerDTO customerDTO, BindingResult bindingResult) {
        try {
            return new ResponseEntity<>(orderService.doCreateOrder(customerDTO).toOrderDTO(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new DataInputException("Thất bại!");
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllOrder() {
        List<OrderDTO> orderDTOS = orderService.findOrderDTO();
        if (orderDTOS.isEmpty()) {
            throw new DataInputException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.findById(id);
        String fullNameCustomer = orderOptional.get().getFullName();
        Order order = orderOptional.get();
        if (!orderOptional.isPresent()){
            throw new DataInputException("không tìm thấy đơn hàng!");
        }
        if (fullNameCustomer.isEmpty()) {
            throw new DataInputException("không tìm thấy đơn hàng!");
        }
        order.setStatus("Đã duyệt đơn hàng!");

        orderService.save(order);
        return new ResponseEntity<>(order.toOrderDTO(), HttpStatus.ACCEPTED);
    }


    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancleOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.findById(id);
        String fullNameCustomer = orderOptional.get().getFullName();
        Order order = orderOptional.get();
        if (!orderOptional.isPresent()){
            throw new DataInputException("không tìm thấy đơn hàng!");
        }
        if (fullNameCustomer.isEmpty()) {
            throw new DataInputException("không tìm thấy đơn hàng!");
        }
        order.setStatus("Đã hủy đơn hàng!");

        orderService.save(order);
        return new ResponseEntity<>(order.toOrderDTO(), HttpStatus.ACCEPTED);
    }

}
