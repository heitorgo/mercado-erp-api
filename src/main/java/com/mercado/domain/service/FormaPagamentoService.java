package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.FormaPagamento;
import com.mercado.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void excluir(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A forma de pagamento de codigo identificador %d está em uso", id));
		}
	}

}
