package com.informatorio.emprendimientos.repository;

import com.informatorio.emprendimientos.entity.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long> {
    //@Query("SELECT e FROM Emprendimiento e join fetch e.tags t WHERE t.name LIKE %:tag%")
    //List<Emprendimiento> findAllByTag(@Param("tag") String tag);
    //List<Emprendimiento> findAllByTags(String tag);
}