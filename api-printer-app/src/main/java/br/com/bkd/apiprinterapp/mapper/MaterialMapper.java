package br.com.bkd.apiprinterapp.mapper;

import br.com.bkd.apiprinterapp.dto.material.request.MaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.response.MaterialResponse;
import br.com.bkd.apiprinterapp.model.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public Material paraEntidade(MaterialRequest dados) {
        Material material = new Material();
        material.setName(dados.name().trim());
        material.setType(dados.type());
        material.setPricePerKg(dados.pricePerKg());
        material.setActive(dados.active() == null || dados.active());
        return material;
    }

    public MaterialResponse paraResposta(Material material) {
        return new MaterialResponse(
                material.getId(),
                material.getName(),
                material.getType(),
                material.getPricePerKg(),
                material.getActive());
    }
}
