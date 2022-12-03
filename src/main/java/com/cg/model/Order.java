package com.cg.model;

import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Accessors(chain = true)
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String fullName;

    private String address;

    @Digits(integer = 12, fraction = 0)
    @Column(name = "grand_total")
    private BigDecimal grandTotal;


    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;



    public OrderDTO toOrderDTO() {
        return new OrderDTO()
                .setId(id)
                .setFullName(fullName)
                .setAddress(address)
                .setGrandTotal(grandTotal)
                .setEmail(email)
                .setUser(user.toUserDTO())
                .setStatus(status)
                .setCustomer(customer.toCustomerDTO())
                .setCreatedAt(getCreatedAt())
                .setUpdatedAT(getUpdatedAt());
    }
}
