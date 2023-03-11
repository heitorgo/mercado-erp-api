package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EmpresaNaoEncontradaException;
import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String msg_empresa_em_uso="Empresa de codigo identificador %d está em uso";
	
	public Empresa salvar(Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	public void excluir(Long id) {
		try {
			empresaRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new EmpresaNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_empresa_em_uso, id));
		}
	}
	
	public Empresa buscarOuFalhar(Long id) {
		return empresaRepository.findById(id).orElseThrow(() ->
		new EmpresaNaoEncontradaException(id));
	}

}
