package com.zup.nosso.banco.digital.repository;

import com.zup.nosso.banco.digital.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findByEmail(String email);

}
