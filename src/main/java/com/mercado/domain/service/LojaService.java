package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.EmpresaRepository;
import com.mercado.domain.repository.LojaRepository;

@Service
public class LojaService {
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Loja salvar(Loja loja) {
		Long empresaId = loja.getEmpresa().getId();
		Empresa empresa = empresaRepository.findById(empresaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(
						"Não existe nenhuma empresa de codigo identificador %d", empresaId)));
		loja.setEmpresa(empresa);
		return lojaRepository.save(loja);
	}
	
	public void excluir(Long id) {
		try {
			lojaRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A loja de codigo identificador %d está em uso", id));
		}
	}

}
