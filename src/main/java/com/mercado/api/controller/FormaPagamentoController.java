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
	public FormaPagamento buscar(@PathVariable Long id){
		return formaPagamentoService.buscarOuFalhar(id);
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> listarPorTitulo(String titulo){
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAllByTituloContaining(titulo);
		if(formasPagamento.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(formasPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento adicionar(@RequestBody @Valid FormaPagamento formaPagamento) {
		return formaPagamentoService.salvar(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public FormaPagamento atualizar( @PathVariable Long id, @RequestBody @Valid FormaPagamento formaPagamento){
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id", "dataCadastro");
		return formaPagamentoService.salvar(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		formaPagamentoService.excluir(id);
	}

}
