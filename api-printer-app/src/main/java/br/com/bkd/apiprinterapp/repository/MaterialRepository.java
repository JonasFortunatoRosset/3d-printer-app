package br.com.bkd.apiprinterapp.repository;

import br.com.bkd.apiprinterapp.enums.MaterialType;
import br.com.bkd.apiprinterapp.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<Material, UUID> {

    @Query("""
            select m from Material m
            where lower(m.name) like lower(concat('%', :nome, '%'))
              and (:tipo is null or m.type = :tipo)
              and (:ativo is null or m.active = :ativo)
            """)
    Page<Material> filtrar(@Param("nome") String nome,
                           @Param("tipo") MaterialType tipo,
                           @Param("ativo") Boolean ativo,
                           Pageable paginacao);

    @Query("select count(m) > 0 from Material m where lower(m.name) = lower(:nome)")
    boolean existeNome(@Param("nome") String nome);

    @Query("select count(m) > 0 from Material m where lower(m.name) = lower(:nome) and m.id <> :id")
    boolean existeNomeEmOutroMaterial(@Param("nome") String nome, @Param("id") UUID id);
}
