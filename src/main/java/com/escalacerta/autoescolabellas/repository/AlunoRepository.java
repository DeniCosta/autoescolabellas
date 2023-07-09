package com.escalacerta.autoescolabellas.repository;


import com.escalacerta.autoescolabellas.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{

}

