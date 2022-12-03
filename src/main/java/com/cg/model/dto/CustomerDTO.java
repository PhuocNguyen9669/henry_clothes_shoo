package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerDTO {
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String urlImage;

    private String address;

    public Customer toCustomer() {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setUrlImage(urlImage)
                .setAddress(address);
    }

}
