package br.com.bkd.apiprinterapp.dto.printer.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatedPrinterResponse(
        UUID id,
        String name,
        String model,
        Integer powerWatts,
        BigDecimal costPerHour,
        Boolean active
)
{ }
