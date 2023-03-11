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

import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.exception.VendaNaoEncontradaException;
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
	public Venda buscar(@PathVariable Long id){
		return vendaService.buscarOuFalhar(id);
	}
	
	@GetMapping("/descricao")
	public ResponseEntity<?> listarPorDescricao(String descricao){
		List<Venda> vendas = vendaRepository.findAllByDescricaoContaining(descricao);
		if(vendas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(vendas);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Venda adicionar(@RequestBody @Valid Venda venda){
		try {
			return vendaService.salvar(venda);
		}catch(VendaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Venda atualizar( @PathVariable Long id, @RequestBody @Valid Venda venda){
		Venda vendaAtual = vendaService.buscarOuFalhar(id);
		BeanUtils.copyProperties(venda, vendaAtual, "id", "dataCadastro");
		try {
			return vendaService.salvar(vendaAtual);
		}catch(VendaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		vendaService.excluir(id);
	}

}
