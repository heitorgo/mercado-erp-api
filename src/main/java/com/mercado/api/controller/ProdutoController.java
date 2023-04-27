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

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Produto buscar(@PathVariable Long id) {
		return produtoService.buscarOuFalhar(id);
	}

	@GetMapping("/nome")
	public ResponseEntity<?> listarPorNome(String nome) {
		List<Produto> produtos = produtoRepository.findAllByNomeContaining(nome);
		if (produtos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto adicionar(@RequestBody @Valid Produto produto) {
		try {
			return produtoService.salvar(produto);
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public Produto atualizar(@PathVariable Long id, @RequestBody @Valid Produto produto) {
		Produto produtoAtual = produtoService.buscarOuFalhar(id);
		BeanUtils.copyProperties(produto, produtoAtual, "id", "dataCadastro");
		try {
			return produtoService.salvar(produtoAtual);
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
