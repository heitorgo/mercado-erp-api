package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.CargoNaoEncontradoException;
import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.CargoRepository;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private LojaService lojaService;
	
	private static final String msg_cargo_em_uso="Cargo de codigo identificador %d está em uso";
	
	public Cargo salvar(Cargo cargo) {
		Long lojaId = cargo.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		cargo.setLoja(loja);
		return cargoRepository.save(cargo);
	}
	
	public void excluir(Long id) {
		try {
			cargoRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new CargoNaoEncontradoException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_cargo_em_uso, id));
		}
	}
	
	public Cargo buscarOuFalhar(Long id) {
		return cargoRepository.findById(id).orElseThrow(() ->
		new CargoNaoEncontradoException(id));
	}

}
