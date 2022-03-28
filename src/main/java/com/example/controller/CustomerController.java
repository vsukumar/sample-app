package com.example.controller;

import com.example.model.Customer;
import com.example.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Customer getCustomerByLastName(@RequestParam String lastName) {
        return customerService.getCustomerByLastName(lastName);
    }

    @PostMapping
    @ResponseBody
    public Customer post(@RequestBody String object) throws JsonMappingException, JsonProcessingException {
        Customer customerObject = objectMapper.readValue(object, Customer.class);
        Customer customer = Customer.builder()
                .firstName(customerObject.getFirstName())
                .lastName(customerObject.getLastName())
                .email(customerObject.getEmail())
                .password(customerObject.getPassword()).build();
        customer = customerService.save(customer);
        return customer;
    }

}
