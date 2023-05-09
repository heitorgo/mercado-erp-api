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

import com.mercado.api.assembler.venda.VendaInputDisassembler;
import com.mercado.api.assembler.venda.VendaModelAssembler;
import com.mercado.api.model.VendaModel;
import com.mercado.api.model.input.venda.VendaInput;
import com.mercado.domain.exception.CaixaNaoEncontradoException;
import com.mercado.domain.exception.FuncionarioNaoEncontradoException;
import com.mercado.domain.exception.NegocioException;
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
	
	@Autowired
	private VendaModelAssembler vendaModelAssembler;
	
	@Autowired
	private VendaInputDisassembler vendaInputDisassembler;

	@GetMapping
	public List<VendaModel> listar() {
		List<Venda> vendas = vendaRepository.findAll();
		return vendaModelAssembler.toCollectionModel(vendas);
	}

	@GetMapping("/{id}")
	public VendaModel buscar(@PathVariable Long id) {
		return vendaModelAssembler.toModel(vendaService.buscarOuFalhar(id));
	}

	@GetMapping("/descricao")
	public ResponseEntity<?> listarPorDescricao(String descricao) {
		List<Venda> vendas = vendaRepository.findAllByDescricaoContaining(descricao);
		if (vendas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(vendaModelAssembler.toCollectionModel(vendas));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendaModel adicionar(@RequestBody @Valid VendaInput vendaInput) {
		try {
			Venda venda = vendaInputDisassembler.toDomainModel(vendaInput);
			return vendaModelAssembler.toModel(vendaService.salvar(venda));
		} catch (CaixaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		} catch (FuncionarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public VendaModel atualizar(@PathVariable Long id, @RequestBody @Valid VendaInput vendaInput) {
		try {
			Venda vendaAtual = vendaService.buscarOuFalhar(id);
			vendaInputDisassembler.copyToDomainObject(vendaInput, vendaAtual);
			return vendaModelAssembler.toModel(vendaService.salvar(vendaAtual));
		} catch (CaixaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		} catch (FuncionarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		vendaService.excluir(id);
	}

}
