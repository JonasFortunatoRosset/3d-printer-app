package br.com.bkd.apiprinterapp.dto.printer.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreatePrinterRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String name,
        @NotBlank(message = "O modelo é obrigatório")
        @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
        String model,
        @NotNull(message = "A potência em watts é obrigatória")
        @Positive(message = "A potência em watts deve ser maior que zero")
        Integer powerWatts,
        @NotNull(message = "O custo por hora é obrigatório")
        @Positive(message = "O custo por hora deve ser maior que zero")
        @Digits(integer = 8, fraction = 2, message = "O custo por hora deve ter no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal costPerHour,
        Boolean active
)
{ }
