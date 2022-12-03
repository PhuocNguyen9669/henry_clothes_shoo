package com.cg.service.order;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.*;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

//    @Override
//    public List<OrderDTO> findOrderDTOByUserNameAndTime(String username) {
//        return orderRepository.findOrderDTOByUserNameAndTime(username);
//    }


    @Override
    public List<OrderDTO> findOrderDTO() {
        return orderRepository.findOrderDTO();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void softDelete(Order order) {
        order.setDeleted(true);
        orderRepository.save(order);
    }


    @Override
    public Order doCreateOrder(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(customerDTO.toCustomer());
        Order order = new Order();
        Optional<UserDTO> userDTO = userRepository.findUserDTOFullByUsername(customer.getEmail());
        order.setCustomer(customer);
        order.setUser(userDTO.get().toUser());
        order.setStatus("Đang chờ duyệt");
        order.setGrandTotal(BigDecimal.valueOf(0));
        order.setId(0L);
        order.setAddress(customer.getAddress());
        order.setFullName(customer.getFullName());
        order.setEmail(customer.getEmail());
        Order orderNew = orderRepository.save(order);
        List<CartItemDTO> cartItemList = cartItemRepository.getCartItemByCustomerEmail(customer.getEmail());
        for (CartItemDTO cartItemDTO : cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderNew);
            orderItem.setId(0L);
            orderItem.setTotalPrice(cartItemDTO.getTotalPrice());
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setTitle(cartItemDTO.getTitle());
            orderItem.setPrice(cartItemDTO.getPrice());
            orderItemRepository.save(orderItem);
            cartItemRepository.deleteById(cartItemDTO.getId());
        }
        Optional<CartInfoDTO> cartInfoDTO = cartRepository.getCartInfoDTOByUserDTO(customer.getEmail());
        orderNew.setGrandTotal(cartInfoDTO.get().getGrandTotal());
        cartRepository.deleteById(cartInfoDTO.get().getId());
        return orderRepository.save(orderNew);
    }

    @Override
    public Optional<OrderDTO> findOrderDTOById(Long id) {
//        return orderRepository.findOrderDTOById(id);
        return  null;
    }

//    @Override
//    public Optional<OrderDTO> findOrderDTOByFullName(String customer) {
//        return orderRepository.findOrderDTOByFullName(customer);
//    }


    @Override
    public Optional<OrderDTO> findOrderDTOByFullName(String customer) {
        return orderRepository.findUserDTOByFullName(customer);
    }

    @Override
    public Order doUpdateOrder(Customer customerDTO) {
        Optional<OrderDTO> orderOptional = orderRepository.findUserDTOByFullName(customerDTO.getFullName());
        OrderDTO orderDTO = orderOptional.get();
        if (!orderOptional.isPresent()){
            throw new DataInputException("Không tìm thấy đơn hàng!");
        }
        orderDTO.setStatus("Đã duyệt đơn hàng!");
        return orderRepository.save(orderDTO.toOrder());
    }
}
