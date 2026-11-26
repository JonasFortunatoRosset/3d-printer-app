package br.com.bkd.apiprinterapp.dto.user.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedUserResponse(
         UUID id,
         String name,
         String surname,
         String cpf,
         String email,
         String phone,
         UUID role,
         LocalDateTime createdAt)
{ }