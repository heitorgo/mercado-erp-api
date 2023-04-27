package com.mercado.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.ProdutoNaoEncontradoException;
import com.mercado.domain.model.Loja;
import com.mercado.domain.model.Produto;
import com.mercado.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private LojaService lojaService;

	private final static String msg_produto_em_uso = "Produto de codigo identificador %d está em uso";

	@Transactional
	public Produto salvar(Produto produto) {
		Long lojaId = produto.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		produto.setLoja(loja);
		return produtoRepository.save(produto);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			produtoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_produto_em_uso, id));
		}
	}

	public Produto buscarOuFalhar(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}

}
