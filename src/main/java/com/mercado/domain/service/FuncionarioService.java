package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.FuncionarioNaoEncontradoException;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private static final String msg_funcionario_em_uso="O funcionario de codigo identificador %d está em uso";
	
	public Funcionario salvar(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}
	
	public void excluir(Long id) {
		try {
			funcionarioRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new FuncionarioNaoEncontradoException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_funcionario_em_uso, id));
		}
	}
	
	public Funcionario buscarOuFalhar(Long id) {
		return funcionarioRepository.findById(id).orElseThrow(() ->
		new FuncionarioNaoEncontradoException(id));
	}

}
