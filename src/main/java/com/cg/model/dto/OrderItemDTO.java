package com.cg.model.dto;

import com.cg.model.Order;
import com.cg.model.OrderItem;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
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
public class OrderItemDTO {

    private Long id;

    private String title;

    private BigDecimal price;

    private int quantity;

    private BigDecimal totalPrice;

    private OrderDTO order;

    public OrderItem toOrderItem() {
        return new OrderItem()
                .setId(id)
                .setPrice(price)
                .setQuantity(quantity)
                .setTitle(title)
                .setTotalPrice(totalPrice)
                .setOrder(order.toOrder());
    }
}
