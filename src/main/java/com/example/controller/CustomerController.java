package com.example.controller;

import com.example.client.reqres.ReqResClient;
import com.example.client.reqres.ReqResUser;
import com.example.model.Customer;
import com.example.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.client.reqres.ReqResClient;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReqResClient reqResClient;

    @GetMapping
    public ResponseEntity<Customer> getCustomerByLastName(@RequestParam String lastName) {

        ReqResUser reqResUser = reqResClient.getReqResUser(2);

        System.out.println(reqResUser);

        Customer customer = new Customer();
        customer.setLastName(reqResUser.getLast_name());
        customer.setEmail(reqResUser.getEmail());

//        Customer customer = customerService.getCustomerByLastName(lastName);
//        if (customer ==null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }

        reqResClient.createReqResUser("some name");

        return ResponseEntity.status(HttpStatus.OK)
                .body(customer);
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
