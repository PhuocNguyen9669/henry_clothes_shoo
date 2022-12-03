package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<OrderDTO> findOrderDTOByUserNameAndTime(String username);
    @Query("SELECT NEW com.cg.model.dto.OrderDTO (" +
            "o.id, " +
            "o.email, " +
            "o.fullName, " +
            "o.address, " +
            "o.grandTotal, " +
            "o.status, " +
            "o.user, " +
            "o.customer, " +
            "o.createdAt, " +
            "o.updatedAt)" +
            " FROM Order o")
    List<OrderDTO> findOrderDTO();


//    @Query("SELECT NEW com.cg.model.dto.OrderDTO (" +
//            "o.id, " +
//            "o.email, " +
//            "o.fullName, " +
//            "o.address, " +
//            "o.grandTotal, " +
//            "o.status, " +
//            "o.user, " +
//            "o.customer, " +
//            "o.createdAt, " +
//            "o.updatedAt) FROM Order o " +
//            "WHERE o.id = ?1")
//    Optional<OrderDTO> findOrderDTOById(Long id);


    @Query("SELECT NEW com.cg.model.dto.OrderDTO (o.id, o.customer) FROM Order o WHERE o.customer.fullName = ?1")
    Optional<OrderDTO> findUserDTOByFullName(String fullName);

}
