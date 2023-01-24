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
import com.mercado.domain.model.Venda;
import com.mercado.domain.repository.VendaRepository;
import com.mercado.domain.service.VendaService;

@RequestMapping("/vendas")
@RestController
public class VendaController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private VendaService vendaService;
	
	@GetMapping
	public List<Venda> listar() {
		return vendaRepository.findAll(); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscar(@PathVariable Long id){
		Optional<Venda> venda = vendaRepository.findById(id);
		if(venda.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(venda.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Venda venda){
		try {
			vendaService.salvar(venda);
			return ResponseEntity.status(HttpStatus.CREATED).body(venda);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Venda venda){
		try {
			Optional<Venda> vendaAtual = vendaRepository.findById(id);
			if(vendaAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			BeanUtils.copyProperties(venda, vendaAtual.get(), "id", "dataCadastro");
			Venda vendaSalva = vendaService.salvar(vendaAtual.get());
			return ResponseEntity.ok(vendaSalva);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<Venda> venda = vendaRepository.findById(id);
			if(venda.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			vendaService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
