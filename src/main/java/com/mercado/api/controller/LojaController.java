package com.mercado.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.domain.exception.LojaNaoEncontradaException;
import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.model.Loja;
import com.mercado.domain.repository.LojaRepository;
import com.mercado.domain.service.LojaService;

@RequestMapping("/lojas")
@RestController
public class LojaController {
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@Autowired
	private LojaService lojaService;
	
	@GetMapping
	public List<Loja> listar() {
		return lojaRepository.findAll(); 
	}
	
	@GetMapping("/{id}")
	public Loja buscar(@PathVariable Long id){
		return lojaService.buscarOuFalhar(id);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome){
		List<Loja> lojas = lojaRepository.findAllByNomeContaining(nome);
		if(lojas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lojas);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Loja adicionar(@RequestBody @Valid Loja loja){
		try {
			return lojaService.salvar(loja);
		}catch(LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Loja atualizar( @PathVariable Long id, @RequestBody @Valid Loja loja){
		Loja lojaAtual = lojaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(loja, lojaAtual, "id", "dataCadastro");
		try {
			return lojaService.salvar(lojaAtual);
		}catch(LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		lojaService.excluir(id);
	}

}
