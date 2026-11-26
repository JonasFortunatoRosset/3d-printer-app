package br.com.bkd.apiprinterapp.dto.user.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateUserRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O sobrenome é obrigatório")
        String surname,
        @NotBlank(message = "O CPF é obrigatório") @CPF(message = "O cpf é inválido")
        String cpf,
        @NotBlank
        @Email(message = "Formato de e-mail inválido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String password,
        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$", message = "O celular deve conter DDD + número (10 ou 11 dígitos)")
        String phone
)
{ }