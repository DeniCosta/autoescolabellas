package com.escalacerta.autoescolabellas.service;

import java.util.List;
import java.util.Optional;

import com.escalacerta.autoescolabellas.model.Instrutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.escalacerta.autoescolabellas.repository.InstrutorRepository;

@Service
public class InstrutorServiceImpl implements InstrutorService {

    @Autowired
    private InstrutorRepository instrutorRepository;

    @Override
    public List<Instrutor> getAllInstrutores() {
        return instrutorRepository.findAll();
    }

    @Override
    public void saveInstrutor(Instrutor instrutor) {
        this.instrutorRepository.save(instrutor);
    }

    @Override
    public Instrutor getInstrutorById(long id) {
        Optional<Instrutor> optional = instrutorRepository.findById(id);
        Instrutor instrutor = null;
        if (optional.isPresent()) {
            instrutor = optional.get();
        } else {
            throw new RuntimeException("Instrutor não encontrado com o id :: " + id);
        }
        return instrutor;
    }

    @Override
    public void deleteInstrutorById(long id) {
        this.instrutorRepository.deleteById(id);
    }

    @Override
    public Page<Instrutor> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.instrutorRepository.findAll(pageable);
    }
}
