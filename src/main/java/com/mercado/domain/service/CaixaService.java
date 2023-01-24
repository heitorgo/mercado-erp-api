package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.CaixaRepository;
import com.mercado.domain.repository.LojaRepository;

@Service
public class CaixaService {
	
	@Autowired
	private CaixaRepository caixaRepository;
	
	@Autowired
	private LojaRepository lojaRepository;
	
	public Caixa salvar(Caixa caixa) {
		Long lojaId = caixa.getLoja().getId();
		Loja loja = lojaRepository.findById(lojaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(
						"Não existe nenhuma loja de codigo identificador %d", lojaId)));
		caixa.setLoja(loja);
		return caixaRepository.save(caixa);
	}
	
	public void excluir(Long id) {
		try {
			caixaRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O caixa de codigo identificador %d está em uso", id));
		}
	}

}
