package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyExistingObjectException;
import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.CustomerDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import com.nicolasortiz.ecommerce.model.mapper.CustomerMapper;
import com.nicolasortiz.ecommerce.model.mapper.UserMapper;
import com.nicolasortiz.ecommerce.repository.ICustomerRepository;
import com.nicolasortiz.ecommerce.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(CustomerMapper.INSTANCE::toDto);
    }

    @Override
    public CustomerDto findById(int id) {
        return CustomerMapper.INSTANCE.toDto(customerRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("No se encontró al cliente")));
    }

    @Override
    public CustomerDto findByEmail(String email) {
        return CustomerMapper.INSTANCE.toDto(customerRepository.findByEmail(email)
                .orElseThrow(()-> new MyNotFoundException("No se encontró al cliente con ese email")));
    }

    @Override
    public CustomerDto save(Customer customer) {
        Optional<Customer> customerFound = customerRepository.findByEmail(customer.getEmail());

        if (customerFound.isPresent()){
            throw new MyExistingObjectException("Ese email ya está en uso");
        }
        return CustomerMapper.INSTANCE.toDto(customerRepository.save(customer));
    }
}
