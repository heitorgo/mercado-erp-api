package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public Funcionario salvar(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
		
	}
	
	public void excluir(Long id) {
		try {
			funcionarioRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O funcionario de codigo identificador %d está em uso", id));
		}
	}

}
