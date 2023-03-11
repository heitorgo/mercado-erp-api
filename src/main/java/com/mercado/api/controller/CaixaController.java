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

import com.mercado.domain.exception.CaixaNaoEncontradoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.exception.NegocioException;
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
	public Caixa buscar(@PathVariable Long id){
		return caixaService.buscarOuFalhar(id);
	}
	
	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome){
		List<Caixa> caixas = caixaRepository.findAllByNomeContaining(nome);
		if(caixas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(caixas);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Caixa adicionar(@RequestBody @Valid Caixa caixa){
		try {
			return caixaService.salvar(caixa);
		}catch(CaixaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Caixa atualizar( @PathVariable Long id, @RequestBody @Valid Caixa caixa){
		Caixa caixaAtual = caixaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(caixa, caixaAtual, "id", "dataCadastro");
		try {
			return caixaService.salvar(caixaAtual);
		}catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		caixaService.excluir(id);
	}

}
