package com.mercado.api.assembler.produto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.produto.ProdutoAlteracaoInput;
import com.mercado.api.model.input.produto.ProdutoInput;
import com.mercado.domain.model.Loja;
import com.mercado.domain.model.Produto;

@Component
public class ProdutoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainModel(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoAlteracaoInput produtoAlteracaoInput, Produto produto) {
		//para evitar erro de alteração de identificador de loja
		produto.setLoja(new Loja());
		
		modelMapper.map(produtoAlteracaoInput, produto);
	}

}
