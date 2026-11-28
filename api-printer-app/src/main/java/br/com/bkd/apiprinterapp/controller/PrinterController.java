package br.com.bkd.apiprinterapp.controller;

import br.com.bkd.apiprinterapp.dto.printer.request.CreatePrinterRequest;
import br.com.bkd.apiprinterapp.dto.printer.request.UpdatePrinterRequest;
import br.com.bkd.apiprinterapp.dto.printer.response.CreatedPrinterResponse;
import br.com.bkd.apiprinterapp.dto.printer.response.PrinterResponse;
import br.com.bkd.apiprinterapp.service.PrinterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/printers")
public class PrinterController {

    private final PrinterService printerService;

    public PrinterController(PrinterService printerService) {
        this.printerService = printerService;
    }

    @GetMapping
    public Page<PrinterResponse> getAllPrinters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return printerService.getAllPrinters(name, model, active, pageable);
    }

    @GetMapping("/{id}")
    public PrinterResponse getPrinterById(@PathVariable UUID id) {
        return printerService.getPrinterById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedPrinterResponse createPrinter(@RequestBody @Valid CreatePrinterRequest printerDto) {
        return printerService.createPrinter(printerDto);
    }

    @PutMapping("/{id}")
    public PrinterResponse updatePrinter(@PathVariable UUID id, @RequestBody @Valid UpdatePrinterRequest printerDto) {
        return printerService.updatePrinter(id, printerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrinter(@PathVariable UUID id) {
        printerService.deletePrinter(id);
    }
}
