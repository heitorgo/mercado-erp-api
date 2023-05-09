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

import com.mercado.api.assembler.produto.ProdutoInputDisassembler;
import com.mercado.api.assembler.produto.ProdutoModelAssembler;
import com.mercado.api.model.ProdutoModel;
import com.mercado.api.model.input.produto.ProdutoAlteracaoInput;
import com.mercado.api.model.input.produto.ProdutoInput;
import com.mercado.domain.exception.LojaNaoEncontradaException;
import com.mercado.domain.exception.NegocioException;
import com.mercado.domain.model.Produto;
import com.mercado.domain.repository.ProdutoRepository;
import com.mercado.domain.service.ProdutoService;

@RequestMapping("/produtos")
@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@GetMapping
	public List<ProdutoModel> listar() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtoModelAssembler.toCollectionModel(produtos);
	}

	@GetMapping("/{id}")
	public ProdutoModel buscar(@PathVariable Long id) {
		return produtoModelAssembler.toModel(produtoService.buscarOuFalhar(id));
	}

	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome) {
		List<Produto> produtos = produtoRepository.findAllByNomeContaining(nome);
		if (produtos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoModelAssembler.toCollectionModel(produtos));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@RequestBody @Valid ProdutoInput produtoInput) {
		try {
			Produto produto = produtoInputDisassembler.toDomainModel(produtoInput);
			return produtoModelAssembler.toModel(produtoService.salvar(produto));
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public ProdutoModel atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoAlteracaoInput produtoAlteracaoInput) {
		try {
			Produto produtoAtual = produtoService.buscarOuFalhar(id);
			produtoInputDisassembler.copyToDomainObject(produtoAlteracaoInput, produtoAtual);
			return produtoModelAssembler.toModel(produtoService.salvar(produtoAtual));
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		produtoService.excluir(id);
	}

}
