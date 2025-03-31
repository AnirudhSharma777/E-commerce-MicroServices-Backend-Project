package com.ecommerce.Services;


import com.ecommerce.Dto.CustomerRequest;
import com.ecommerce.Entities.Customer;
import com.ecommerce.Exception.CustomerNotFoundException;
import com.ecommerce.Repositories.CustomerRepository;
import com.ecommerce.ResponseDto.CustomerResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    @Override
    public String createCustomer(CustomerRequest request) {
        var customer = this.customerRepository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    @Override
    public boolean existsById(String id) {
        return customerRepository.findById(id).isPresent();
    }

    @Override
    public CustomerResponse findCustomerById(String id) {
        return customerRepository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("No customer found with the provided ID: %s", id)
                ));

    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this.mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided Id: %s",request.id())
                ));
        mergeCustomer(customer,request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }
}
