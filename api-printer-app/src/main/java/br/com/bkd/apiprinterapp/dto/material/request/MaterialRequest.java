package br.com.bkd.apiprinterapp.dto.material.request;

import br.com.bkd.apiprinterapp.enums.MaterialType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record MaterialRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,
        @NotNull(message = "O tipo é obrigatório (PLA, ABS, PETG, TPU ou RESIN)")
        MaterialType type,
        @NotNull(message = "O preço por kg é obrigatório")
        @Positive(message = "O preço por kg deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "O preço por kg deve ter no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal pricePerKg,
        Boolean active
)
{ }
