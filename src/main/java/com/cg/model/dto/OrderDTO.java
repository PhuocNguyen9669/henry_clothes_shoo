package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Order;
import com.cg.model.OrderItem;
import com.cg.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {
    private Long id;

    private String email;

    private String fullName;

    private String address;

    private BigDecimal grandTotal;

    private String status;

    private UserDTO user;

    private CustomerDTO customer;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAT;

    public OrderDTO(Long id, String email, String fullName, String address, BigDecimal grandTotal, String status, User user, Customer customer, Date createdAt, Date updatedAT) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.grandTotal = grandTotal;
        this.status = status;
        this.user = user.toUserDTO();
        this.customer = customer.toCustomerDTO();
        this.createdAt = createdAt;
        this.updatedAT = updatedAT;
    }

    public OrderDTO(Long id, Customer customer) {
        this.id = id;
        this.customer = customer.toCustomerDTO();
    }

    public Order toOrder() {
        return new Order()
                .setId(id)
                .setGrandTotal(grandTotal)
                .setUser(user.toUser())
                .setEmail(email)
                .setCustomer(customer.toCustomer());
    }
}
