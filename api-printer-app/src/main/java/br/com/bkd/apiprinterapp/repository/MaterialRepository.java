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
            where lower(m.name) like lower(concat('%', :name, '%'))
              and (:type is null or m.type = :type)
              and (:active is null or m.active = :active)
            """)
    Page<Material> search(@Param("name") String name,
                          @Param("type") MaterialType type,
                          @Param("active") Boolean active,
                          Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
