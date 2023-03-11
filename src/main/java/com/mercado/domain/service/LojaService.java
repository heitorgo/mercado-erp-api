package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.LojaNaoEncontradaException;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.LojaRepository;

@Service
public class LojaService {
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	private static final String msg_loja_em_uso = "Loja de codigo identificador %d está em uso";
	
	public Loja salvar(Loja loja) {
		Long empresaId = loja.getEmpresa().getId();
		Empresa empresa = empresaService.buscarOuFalhar(empresaId);
		loja.setEmpresa(empresa);
		return lojaRepository.save(loja);
	}
	
	public void excluir(Long id) {
		try {
			lojaRepository.deleteById(id);
		}catch(LojaNaoEncontradaException e) {
			throw new LojaNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_loja_em_uso, id));
		}
	}
	
	public Loja buscarOuFalhar(Long id) {
		return lojaRepository.findById(id).orElseThrow(() ->
		new LojaNaoEncontradaException(id));
	}

}
