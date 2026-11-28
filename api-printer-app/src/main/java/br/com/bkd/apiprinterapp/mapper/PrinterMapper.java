package br.com.bkd.apiprinterapp.mapper;

import br.com.bkd.apiprinterapp.dto.printer.request.CreatePrinterRequest;
import br.com.bkd.apiprinterapp.dto.printer.response.CreatedPrinterResponse;
import br.com.bkd.apiprinterapp.dto.printer.response.PrinterResponse;
import br.com.bkd.apiprinterapp.model.Printer;
import org.springframework.stereotype.Component;

@Component
public class PrinterMapper {

    public Printer toEntity(CreatePrinterRequest printerDto) {
        Printer printer = new Printer();
        printer.setName(printerDto.name().trim());
        printer.setModel(printerDto.model().trim());
        printer.setPowerWatts(printerDto.powerWatts());
        printer.setCostPerHour(printerDto.costPerHour());
        printer.setActive(printerDto.active() == null || printerDto.active());
        return printer;
    }

    public CreatedPrinterResponse toCreatedResponse(Printer printer) {
        return new CreatedPrinterResponse(
                printer.getId(),
                printer.getName(),
                printer.getModel(),
                printer.getPowerWatts(),
                printer.getCostPerHour(),
                printer.getActive());
    }

    public PrinterResponse toResponse(Printer printer) {
        return new PrinterResponse(
                printer.getId(),
                printer.getName(),
                printer.getModel(),
                printer.getPowerWatts(),
                printer.getCostPerHour(),
                printer.getActive());
    }
}
