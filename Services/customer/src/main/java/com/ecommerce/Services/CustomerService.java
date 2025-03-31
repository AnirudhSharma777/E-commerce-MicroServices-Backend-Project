package com.ecommerce.Services;

import com.ecommerce.Dto.CustomerRequest;
import com.ecommerce.ResponseDto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    public String createCustomer(CustomerRequest request);
    public boolean existsById(String id);
    public CustomerResponse findCustomerById(String id);
    public List<CustomerResponse> findAllCustomers();
    public void deleteCustomer(String id);
    public void updateCustomer(CustomerRequest request);

}
