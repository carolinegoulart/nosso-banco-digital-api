package com.zup.nosso.banco.digital.dto;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CustomerDTO {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String firstName;

    @NotNull(message = "LastName cannot be null")
    @NotEmpty(message = "LastName cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Birthday cannot be null")
    private String birthday;

    @NotNull(message = "CPF cannot be null")
    @CPF(message = "Invalid CPF")
    private String cpf;

    public CustomerDTO(String firstName, String lastName, String email, String birthday, String cpf) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.cpf = cpf;
    }

}
