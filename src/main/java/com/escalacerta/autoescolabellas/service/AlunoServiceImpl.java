package com.escalacerta.autoescolabellas.service;

import java.util.List;
import java.util.Optional;

import com.escalacerta.autoescolabellas.model.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.escalacerta.autoescolabellas.repository.AlunoRepository;

@Service
public class AlunoServiceImpl implements AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;

	@Override
	public List<Aluno> getAllAlunos() {
		return alunoRepository.findAll();
	}

	@Override
	public void saveAluno(Aluno aluno) {
		this.alunoRepository.save(aluno);
	}

	@Override
	public Aluno getAlunoById(long id) {
		Optional<Aluno> optional = alunoRepository.findById(id);
		Aluno aluno = null;
		if (optional.isPresent()) {
			aluno = optional.get();
		} else {
			throw new RuntimeException(" Aluno não encontrado com o id :: " + id);
		}
		return aluno;
	}

	@Override
	public void deleteAlunoById(long id) {
		this.alunoRepository.deleteById(id);
	}

	@Override
	public Page<Aluno> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
				Sort.by(sortField).descending();

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.alunoRepository.findAll(pageable);
	}
}
