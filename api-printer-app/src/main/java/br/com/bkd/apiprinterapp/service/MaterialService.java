package br.com.bkd.apiprinterapp.service;

import br.com.bkd.apiprinterapp.dto.material.request.CreateMaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.request.UpdateMaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.response.CreatedMaterialResponse;
import br.com.bkd.apiprinterapp.dto.material.response.MaterialResponse;
import br.com.bkd.apiprinterapp.enums.MaterialType;
import br.com.bkd.apiprinterapp.exception.ConflictException;
import br.com.bkd.apiprinterapp.exception.ResourceNotFoundException;
import br.com.bkd.apiprinterapp.mapper.MaterialMapper;
import br.com.bkd.apiprinterapp.model.Material;
import br.com.bkd.apiprinterapp.repository.MaterialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.materialMapper = materialMapper;
    }

    @Transactional(readOnly = true)
    public Page<MaterialResponse> getAllMaterials(String name, MaterialType type, Boolean active, Pageable pageable) {
        String nameFilter = name == null ? "" : name.trim();
        return materialRepository.search(nameFilter, type, active, pageable)
                .map(materialMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public MaterialResponse getMaterialById(UUID id) {
        return materialMapper.toResponse(findMaterialById(id));
    }

    @Transactional
    public CreatedMaterialResponse createMaterial(CreateMaterialRequest materialDto) {
        if (materialRepository.existsByNameIgnoreCase(materialDto.name().trim())) {
            throw new ConflictException("Já existe um material com este nome");
        }
        Material material = materialMapper.toEntity(materialDto);
        return materialMapper.toCreatedResponse(materialRepository.save(material));
    }

    @Transactional
    public MaterialResponse updateMaterial(UUID id, UpdateMaterialRequest materialDto) {
        Material material = findMaterialById(id);

        if (materialRepository.existsByNameIgnoreCaseAndIdNot(materialDto.name().trim(), id)) {
            throw new ConflictException("Já existe um material com este nome");
        }

        material.setName(materialDto.name().trim());
        material.setType(materialDto.type());
        material.setPricePerKg(materialDto.pricePerKg());
        if (materialDto.active() != null) {
            material.setActive(materialDto.active());
        }
        return materialMapper.toResponse(materialRepository.save(material));
    }

    @Transactional
    public void deleteMaterial(UUID id) {
        Material material = findMaterialById(id);
        materialRepository.delete(material);
    }

    private Material findMaterialById(UUID id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", id));
    }
}
