package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.FormaPagamentoNaoEncontradaException;
import com.mercado.domain.model.FormaPagamento;
import com.mercado.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	private static final String msg_forma_pagamento_em_uso = "A forma de pagamento de codigo identificador %d está em uso";
	
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void excluir(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new FormaPagamentoNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_forma_pagamento_em_uso, id));
		}
	}
	
	public FormaPagamento buscarOuFalhar(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() ->
		new FormaPagamentoNaoEncontradaException(id));
	}

}
