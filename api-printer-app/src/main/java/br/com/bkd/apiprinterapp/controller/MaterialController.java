package br.com.bkd.apiprinterapp.controller;

import br.com.bkd.apiprinterapp.dto.material.request.CreateMaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.request.UpdateMaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.response.CreatedMaterialResponse;
import br.com.bkd.apiprinterapp.dto.material.response.MaterialResponse;
import br.com.bkd.apiprinterapp.enums.MaterialType;
import br.com.bkd.apiprinterapp.service.MaterialService;
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
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public Page<MaterialResponse> getAllMaterials(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) MaterialType type,
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return materialService.getAllMaterials(name, type, active, pageable);
    }

    @GetMapping("/{id}")
    public MaterialResponse getMaterialById(@PathVariable UUID id) {
        return materialService.getMaterialById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedMaterialResponse createMaterial(@RequestBody @Valid CreateMaterialRequest materialDto) {
        return materialService.createMaterial(materialDto);
    }

    @PutMapping("/{id}")
    public MaterialResponse updateMaterial(@PathVariable UUID id, @RequestBody @Valid UpdateMaterialRequest materialDto) {
        return materialService.updateMaterial(id, materialDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterial(@PathVariable UUID id) {
        materialService.deleteMaterial(id);
    }
}
