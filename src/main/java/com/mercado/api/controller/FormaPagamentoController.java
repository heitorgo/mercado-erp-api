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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.model.FormaPagamento;
import com.mercado.domain.repository.FormaPagamentoRepository;
import com.mercado.domain.service.FormaPagamentoService;

@RequestMapping("/formas-pagamento")
@RestController
public class FormaPagamentoController{
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@GetMapping
	public List<FormaPagamento> listar(){
		return formaPagamentoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamento> buscar(@PathVariable Long id){
		Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);
		if(formaPagamento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(formaPagamento.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento adicionar(@RequestBody FormaPagamento formaPagamento) {
		return formaPagamentoService.salvar(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody FormaPagamento formaPagamento){
		Optional<FormaPagamento> formaPagamentoAtual = formaPagamentoRepository.findById(id);
		if(formaPagamentoAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual.get(), "id", "dataCadastro");
		FormaPagamento formaPagamentoSalva = formaPagamentoService.salvar(formaPagamentoAtual.get());
		return ResponseEntity.ok(formaPagamentoSalva);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);
			if(formaPagamento.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			formaPagamentoService.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
