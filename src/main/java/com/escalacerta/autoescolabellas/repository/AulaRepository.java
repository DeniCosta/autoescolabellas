package com.escalacerta.autoescolabellas.repository;

import java.time.LocalDateTime;
import java.util.List;


import com.escalacerta.autoescolabellas.model.Aula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public interface AulaRepository extends CrudRepository<Aula, Long> {
    @Query("from Aula e where not(e.end < :from or e.start > :to)")
    public List<Aula> findBetween(@Param("from") @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso=ISO.DATE_TIME) LocalDateTime end);
}
