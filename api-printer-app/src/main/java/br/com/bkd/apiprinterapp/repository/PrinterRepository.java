package br.com.bkd.apiprinterapp.repository;

import br.com.bkd.apiprinterapp.model.Printer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrinterRepository extends JpaRepository<Printer, UUID> {

    @Query("""
            select p from Printer p
            where lower(p.name) like lower(concat('%', :name, '%'))
              and lower(p.model) like lower(concat('%', :model, '%'))
              and (:active is null or p.active = :active)
            """)
    Page<Printer> search(@Param("name") String name,
                         @Param("model") String model,
                         @Param("active") Boolean active,
                         Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
