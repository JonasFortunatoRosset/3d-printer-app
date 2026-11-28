package br.com.bkd.apiprinterapp.service;

import br.com.bkd.apiprinterapp.dto.material.request.MaterialRequest;
import br.com.bkd.apiprinterapp.dto.material.response.MaterialResponse;
import br.com.bkd.apiprinterapp.enums.MaterialType;
import br.com.bkd.apiprinterapp.exception.ConflitoException;
import br.com.bkd.apiprinterapp.exception.RecursoNaoEncontradoException;
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

    private final MaterialRepository repositorioMaterial;
    private final MaterialMapper mapeadorMaterial;

    public MaterialService(MaterialRepository repositorioMaterial, MaterialMapper mapeadorMaterial) {
        this.repositorioMaterial = repositorioMaterial;
        this.mapeadorMaterial = mapeadorMaterial;
    }

    @Transactional(readOnly = true)
    public Page<MaterialResponse> listar(String nome, MaterialType tipo, Boolean ativo, Pageable paginacao) {
        String filtroNome = nome == null ? "" : nome.trim();
        return repositorioMaterial.filtrar(filtroNome, tipo, ativo, paginacao)
                .map(mapeadorMaterial::paraResposta);
    }

    @Transactional(readOnly = true)
    public MaterialResponse buscar(UUID id) {
        return mapeadorMaterial.paraResposta(buscarEntidade(id));
    }

    @Transactional
    public MaterialResponse criar(MaterialRequest dados) {
        if (repositorioMaterial.existeNome(dados.name().trim())) {
            throw new ConflitoException("Já existe um material com este nome");
        }
        Material material = mapeadorMaterial.paraEntidade(dados);
        return mapeadorMaterial.paraResposta(repositorioMaterial.save(material));
    }

    @Transactional
    public MaterialResponse atualizar(UUID id, MaterialRequest dados) {
        Material material = buscarEntidade(id);

        if (repositorioMaterial.existeNomeEmOutroMaterial(dados.name().trim(), id)) {
            throw new ConflitoException("Já existe um material com este nome");
        }

        material.setName(dados.name().trim());
        material.setType(dados.type());
        material.setPricePerKg(dados.pricePerKg());
        if (dados.active() != null) {
            material.setActive(dados.active());
        }
        return mapeadorMaterial.paraResposta(repositorioMaterial.save(material));
    }

    @Transactional
    public void excluir(UUID id) {
        Material material = buscarEntidade(id);
        repositorioMaterial.delete(material);
    }

    private Material buscarEntidade(UUID id) {
        return repositorioMaterial.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Material", id));
    }
}
