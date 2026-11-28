package br.com.bkd.apiprinterapp.dto.material.response;

import br.com.bkd.apiprinterapp.enums.MaterialType;

import java.math.BigDecimal;
import java.util.UUID;

public record MaterialResponse(
        UUID id,
        String name,
        MaterialType type,
        BigDecimal pricePerKg,
        Boolean active
)
{ }
