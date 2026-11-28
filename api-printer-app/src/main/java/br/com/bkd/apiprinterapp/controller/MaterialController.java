package br.com.bkd.apiprinterapp.controller;

import br.com.bkd.apiprinterapp.dto.material.request.MaterialRequest;
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

    private final MaterialService servicoMaterial;

    public MaterialController(MaterialService servicoMaterial) {
        this.servicoMaterial = servicoMaterial;
    }

    @GetMapping
    public Page<MaterialResponse> listar(
            @RequestParam(name = "name", required = false) String nome,
            @RequestParam(name = "type", required = false) MaterialType tipo,
            @RequestParam(name = "active", required = false) Boolean ativo,
            @PageableDefault(size = 10, sort = "name") Pageable paginacao) {
        return servicoMaterial.listar(nome, tipo, ativo, paginacao);
    }

    @GetMapping("/{id}")
    public MaterialResponse buscar(@PathVariable UUID id) {
        return servicoMaterial.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialResponse criar(@RequestBody @Valid MaterialRequest dados) {
        return servicoMaterial.criar(dados);
    }

    @PutMapping("/{id}")
    public MaterialResponse atualizar(@PathVariable UUID id, @RequestBody @Valid MaterialRequest dados) {
        return servicoMaterial.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        servicoMaterial.excluir(id);
    }
}
