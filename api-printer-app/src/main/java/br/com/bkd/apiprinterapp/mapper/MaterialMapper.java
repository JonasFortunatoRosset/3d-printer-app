package br.com.bkd.apiprinterapp.mapper;

import br.com.bkd.apiprinterapp.dto.material.request.CreateMaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.response.CreatedMaterialResponse;
import br.com.bkd.apiprinterapp.dto.material.response.MaterialResponse;
import br.com.bkd.apiprinterapp.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public Material toEntity(CreateMaterialRequest materialDto) {
        Material material = new Material();
        material.setName(materialDto.name().trim());
        material.setType(materialDto.type());
        material.setPricePerKg(materialDto.pricePerKg());
        material.setActive(materialDto.active() == null || materialDto.active());
        return material;
    }

    public CreatedMaterialResponse toCreatedResponse(Material material) {
        return new CreatedMaterialResponse(
                material.getId(),
                material.getName(),
                material.getType(),
                material.getPricePerKg(),
                material.getActive());
    }

    public MaterialResponse toResponse(Material material) {
        return new MaterialResponse(
                material.getId(),
                material.getName(),
                material.getType(),
                material.getPricePerKg(),
                material.getActive());
    }
}
