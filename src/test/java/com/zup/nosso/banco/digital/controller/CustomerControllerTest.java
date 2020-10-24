package com.zup.nosso.banco.digital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.nosso.banco.digital.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenDataIsValid_thenReturn201() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Marcia", "Santos", "marcia@gmail.com", "10/10/1990", "05831263088");
        createCustomerRequestSuccess(customerDTO, status().isCreated());
    }

    @Test
    public void whenFirstNameIsNull_thenReturn200() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(null, "Silva", "fulano@gmail.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenFirstNameIsEmpty_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("", "Silva", "fulano@gmail.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenLastNameIsNull_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", null, "fulano@gmail.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenLastNameIsEmpty_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "", "fulano@gmail.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenEmailIsNull_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", null, "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenEmailIsInvalid_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "email.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenCpfIsNull_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "10/10/1990", null);
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenCpfIsInvalid_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "10/10/1990", "00000000000");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenBirthdayIsNull_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", null, "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenBirthdayIsInvalid_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "12/40/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenUserIsYoungerThan18YearsOld_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "12/10/2015", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenBirthdayIsAfterToday_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "12/10/2030", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenEmailAlreadyExists_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "carol@gmail.com", "10/10/1990", "56316076037");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    @Test
    public void whenCpfAlreadyExists_thenReturn400() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO("Maria", "Silva", "fulano@gmail.com", "10/10/1990", "84142790072");
        createCustomerRequestFail(customerDTO, status().isBadRequest());
    }

    private void createCustomerRequestFail(CustomerDTO customerDTO, ResultMatcher status) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String customerString = mapper.writeValueAsString(customerDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/proposal/user")
                .content(String.valueOf(customerString))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status)
                .andReturn();

        assertNotNull(response.getResponse().getContentAsString());
    }

    private void createCustomerRequestSuccess(CustomerDTO customerDTO, ResultMatcher status) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String customerString = mapper.writeValueAsString(customerDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/proposal/user")
                .content(String.valueOf(customerString))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status)
                .andReturn();

        assertNotNull(response.getResponse().getContentAsString());
        assertNotNull(response.getResponse().getHeader("location"));
    }
}
