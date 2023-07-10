package com.escalacerta.autoescolabellas.repository;

import com.escalacerta.autoescolabellas.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

}