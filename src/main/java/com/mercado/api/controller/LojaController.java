package com.mercado.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Loja> buscar(@PathVariable Long id){
		Optional<Loja> loja = lojaRepository.findById(id);
		if(loja.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(loja.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Loja loja){
		try {
			lojaService.salvar(loja);
			return ResponseEntity.status(HttpStatus.CREATED).body(loja);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Loja loja){
		try {
			Optional<Loja> lojaAtual = lojaRepository.findById(id);
			if(lojaAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			BeanUtils.copyProperties(loja, lojaAtual.get(), "id", "dataCadastro");
			Loja lojaSalva = lojaService.salvar(lojaAtual.get());
			return ResponseEntity.ok(lojaSalva);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Loja> loja = lojaRepository.findById(id);
			if(loja.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			lojaService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
