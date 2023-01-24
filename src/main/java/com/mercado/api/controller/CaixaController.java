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
import com.mercado.domain.model.Caixa;
import com.mercado.domain.repository.CaixaRepository;
import com.mercado.domain.service.CaixaService;

@RequestMapping("/caixas")
@RestController
public class CaixaController {
	
	@Autowired
	private CaixaRepository caixaRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	@GetMapping
	public List<Caixa> listar() {
		return caixaRepository.findAll(); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Caixa> buscar(@PathVariable Long id){
		Optional<Caixa> caixa = caixaRepository.findById(id);
		if(caixa.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(caixa.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Caixa caixa){
		try {
			caixaService.salvar(caixa);
			return ResponseEntity.status(HttpStatus.CREATED).body(caixa);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Caixa caixa){
		try {
			Optional<Caixa> caixaAtual = caixaRepository.findById(id);
			if(caixaAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			BeanUtils.copyProperties(caixa, caixaAtual.get(), "id", "dataCadastro");
			Caixa caixaSalvo = caixaService.salvar(caixaAtual.get());
			return ResponseEntity.ok(caixaSalvo);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Caixa> caixa = caixaRepository.findById(id);
			if(caixa.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			caixaService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
