package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.customer.CustomerDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICustomerService {

    Page<CustomerDto> findAll(Pageable pageable);

    CustomerDto findById(int id);

    CustomerDto findByEmail(String email);

    void save(Customer customer);

}
