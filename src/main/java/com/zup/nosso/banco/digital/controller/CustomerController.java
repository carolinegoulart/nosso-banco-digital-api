package com.zup.nosso.banco.digital.controller;

import com.zup.nosso.banco.digital.dto.CustomerDTO;
import com.zup.nosso.banco.digital.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path="/proposal/user", consumes="application/json", produces="application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody CustomerDTO customerDTO) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("location", "teste");

        return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(customerService.createCustomer(customerDTO));
    }

}
