package com.zup.nosso.banco.digital.service;

import com.zup.nosso.banco.digital.domain.Customer;
import com.zup.nosso.banco.digital.dto.CustomerDTO;
import com.zup.nosso.banco.digital.exception.CpfAlreadyRegisteredException;
import com.zup.nosso.banco.digital.exception.EmailAlreadyRegisteredException;
import com.zup.nosso.banco.digital.repository.CustomerRepository;
import com.zup.nosso.banco.digital.util.DateUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Customer createCustomer(CustomerDTO customerDTO) {
        validateIfCpfIsAlreadyPersisted(customerDTO.getCpf());
        validateIfEmailIsAlreadyPersisted(customerDTO.getEmail());
        Customer customer = convertCustomerDtoToCustomer(customerDTO);
        return customerRepository.save(customer);
    }

    public void validateIfCpfIsAlreadyPersisted(String cpf) {
        Optional<Customer> customer = customerRepository.findByCpf(cpf);
        if (customer.isPresent()) {
            throw new CpfAlreadyRegisteredException();
        }
    }

    public void validateIfEmailIsAlreadyPersisted(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
    }

    public Customer convertCustomerDtoToCustomer(CustomerDTO customerDTO) {
        Converter<String, LocalDateTime> toLocalDateTime =
                ctx -> ctx.getSource() == null ? null : DateUtils.validateBirthdayAndConvertToLocalDateTime(ctx.getSource());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(CustomerDTO.class, Customer.class).addMappings(mapper -> {
            mapper.map(CustomerDTO::getFirstName, Customer::setFirstName);
            mapper.map(CustomerDTO::getLastName, Customer::setLastName);
            mapper.map(CustomerDTO::getEmail, Customer::setEmail);
            mapper.using(toLocalDateTime).map(CustomerDTO::getBirthday, Customer::setBirthday);
            mapper.map(CustomerDTO::getCpf, Customer::setCpf);
        });

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customerRepository.save(customer);
    }

}
