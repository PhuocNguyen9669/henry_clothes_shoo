package com.cg.service.order;

import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface OrderService extends IGeneralService<Order> {
    Order doCreateOrder(CustomerDTO customerDTO);

//    List<OrderDTO> findOrderDTOByUserNameAndTime(String username);
    List<OrderDTO> findOrderDTO();

    Optional<OrderDTO> findOrderDTOByFullName(String customer);

    Optional<OrderDTO> findOrderDTOById(Long id);



    Order doUpdateOrder(Customer customerDTO);
}

