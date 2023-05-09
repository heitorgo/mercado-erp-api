package com.mercado.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.mercado.api.assembler.loja.LojaInputDisassembler;
import com.mercado.api.assembler.loja.LojaModelAssembler;
import com.mercado.api.model.LojaModel;
import com.mercado.api.model.input.loja.LojaAlteracaoInput;
import com.mercado.api.model.input.loja.LojaInput;
import com.mercado.domain.exception.EmpresaNaoEncontradaException;
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
	
	@Autowired
	private LojaModelAssembler lojaModelAssembler;
	
	@Autowired
	private LojaInputDisassembler lojaInputDisassembler;

	@GetMapping
	public List<LojaModel> listar() {
		List<Loja> lojas = lojaRepository.findAll();
		return lojaModelAssembler.toCollectionModel(lojas);
	}

	@GetMapping("/{id}")
	public LojaModel buscar(@PathVariable Long id) {
		return lojaModelAssembler.toModel(lojaService.buscarOuFalhar(id));
	}

	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome) {
		List<Loja> lojas = lojaRepository.findAllByNomeContaining(nome);
		if (lojas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lojaModelAssembler.toCollectionModel(lojas));

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LojaModel adicionar(@RequestBody @Valid LojaInput lojaInput) {
		try {
			Loja loja = lojaInputDisassembler.toDomainModel(lojaInput);
			return lojaModelAssembler.toModel(lojaService.salvar(loja));
		} catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public LojaModel atualizar(@PathVariable Long id, @RequestBody @Valid LojaAlteracaoInput lojaAlteracaoInput) {
		try {
			Loja lojaAtual = lojaService.buscarOuFalhar(id);
			lojaInputDisassembler.copyToDomainObject(lojaAlteracaoInput, lojaAtual);
			return lojaModelAssembler.toModel(lojaService.salvar(lojaAtual));
		} catch (EmpresaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		lojaService.excluir(id);
	}

}
