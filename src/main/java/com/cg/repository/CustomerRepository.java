package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface    CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByDeletedIsFalse();

    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIdIsNot(String email, Long id);

    Boolean existsByIdAndDeletedIsFalse(Long id);


    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.email, " +
            "c.phone, " +
            "c.urlImage, " +
            "c.address " +
            ") " +
            "FROM Customer c " +
            "WHERE c.deleted = false ")
    List<CustomerDTO> findAllCustomerDTOByDeletedIsFalse();

    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.email, " +
            "c.phone, " +
            "c.urlImage, " +
            "c.address " +
            ") " +
            "FROM Customer c " +
            "WHERE c.id = ?1 ")
    Optional<CustomerDTO> findCustomerDTOById(Long id);

    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.email, " +
            "c.phone, " +
            "c.urlImage, " +
            "c.address " +
            ") " +
            "FROM Customer c " +
            "WHERE c.id = ?1 ")
    CustomerDTO getCustomerDTOById(Long id);

}
