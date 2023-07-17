package com.escalacerta.autoescolabellas.repository;

import com.escalacerta.autoescolabellas.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Aula, Long> {
}