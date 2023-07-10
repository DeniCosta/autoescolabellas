package com.escalacerta.autoescolabellas.service;

import com.escalacerta.autoescolabellas.model.Instrutor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstrutorService {
    List<Instrutor> getAllInstrutores();
    void saveInstrutor(Instrutor instrutor);
    Instrutor getInstrutorById(long id);
    void deleteInstrutorById(long id);
    Page<Instrutor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}