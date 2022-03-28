package com.example.service;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomerByLastName(String name){
        List<Customer> customerList = customerRepository.findByLastName(name);
        if(customerList.size() > 0) {
            return customerList.get(0);
        }
        else return null;
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
}
