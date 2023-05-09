package com.mercado.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.CaixaNaoEncontradoException;
import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.CaixaRepository;

@Service
public class CaixaService {

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private LojaService lojaService;

	private final static String msg_caixa_em_uso = "Caixa de codigo identificador %d está em uso";

	@Transactional
	public Caixa salvar(Caixa caixa) {
		Long lojaId = caixa.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		caixa.setLoja(loja);
		return caixaRepository.save(caixa);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			caixaRepository.deleteById(id);
			caixaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CaixaNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_caixa_em_uso, id));
		}
	}

	public Caixa buscarOuFalhar(Long id) {
		return caixaRepository.findById(id).orElseThrow(() -> new CaixaNaoEncontradoException(id));
	}

}
