package br.com.bkd.apiprinterapp.service;

import br.com.bkd.apiprinterapp.dto.printer.request.CreatePrinterRequest;
import br.com.bkd.apiprinterapp.dto.printer.request.UpdatePrinterRequest;
import br.com.bkd.apiprinterapp.dto.printer.response.CreatedPrinterResponse;
import br.com.bkd.apiprinterapp.dto.printer.response.PrinterResponse;
import br.com.bkd.apiprinterapp.exception.ConflictException;
import br.com.bkd.apiprinterapp.exception.ResourceNotFoundException;
import br.com.bkd.apiprinterapp.mapper.PrinterMapper;
import br.com.bkd.apiprinterapp.model.Printer;
import br.com.bkd.apiprinterapp.repository.PrinterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PrinterService {

    private final PrinterRepository printerRepository;
    private final PrinterMapper printerMapper;

    public PrinterService(PrinterRepository printerRepository, PrinterMapper printerMapper) {
        this.printerRepository = printerRepository;
        this.printerMapper = printerMapper;
    }

    @Transactional(readOnly = true)
    public Page<PrinterResponse> getAllPrinters(String name, String model, Boolean active, Pageable pageable) {
        String nameFilter = name == null ? "" : name.trim();
        String modelFilter = model == null ? "" : model.trim();
        return printerRepository.search(nameFilter, modelFilter, active, pageable)
                .map(printerMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PrinterResponse getPrinterById(UUID id) {
        return printerMapper.toResponse(findPrinterById(id));
    }

    @Transactional
    public CreatedPrinterResponse createPrinter(CreatePrinterRequest printerDto) {
        if (printerRepository.existsByNameIgnoreCase(printerDto.name().trim())) {
            throw new ConflictException("Já existe uma impressora com este nome");
        }
        Printer printer = printerMapper.toEntity(printerDto);
        return printerMapper.toCreatedResponse(printerRepository.save(printer));
    }

    @Transactional
    public PrinterResponse updatePrinter(UUID id, UpdatePrinterRequest printerDto) {
        Printer printer = findPrinterById(id);

        if (printerRepository.existsByNameIgnoreCaseAndIdNot(printerDto.name().trim(), id)) {
            throw new ConflictException("Já existe uma impressora com este nome");
        }

        printer.setName(printerDto.name().trim());
        printer.setModel(printerDto.model().trim());
        printer.setPowerWatts(printerDto.powerWatts());
        printer.setCostPerHour(printerDto.costPerHour());
        if (printerDto.active() != null) {
            printer.setActive(printerDto.active());
        }
        return printerMapper.toResponse(printerRepository.save(printer));
    }

    @Transactional
    public void deletePrinter(UUID id) {
        Printer printer = findPrinterById(id);
        printerRepository.delete(printer);
    }

    private Printer findPrinterById(UUID id) {
        return printerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Impressora", id));
    }
}
