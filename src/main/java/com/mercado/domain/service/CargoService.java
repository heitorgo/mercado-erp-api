package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.CargoRepository;
import com.mercado.domain.repository.LojaRepository;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private LojaRepository lojaRepository;
	
	public Cargo salvar(Cargo cargo) {
		Long lojaId = cargo.getLoja().getId();
		Loja loja = lojaRepository.findById(lojaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(
						"A loja de codigo identificador %d não existe", lojaId)));
		cargo.setLoja(loja);
		return cargoRepository.save(cargo);
	}
	
	public void excluir(Long id) {
		try {
			cargoRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O cargo de codigo identificador %d está em uso", id));
		}
	}

}
