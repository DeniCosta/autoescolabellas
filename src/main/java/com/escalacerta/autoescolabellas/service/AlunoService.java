package com.escalacerta.autoescolabellas.service;


import com.escalacerta.autoescolabellas.model.Aluno;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AlunoService {
    List<Aluno> getAllAlunos();
    void saveAluno(Aluno aluno);
    Aluno getAlunoById(long id);
    void deleteAlunoById(long id);
    Page<Aluno> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}