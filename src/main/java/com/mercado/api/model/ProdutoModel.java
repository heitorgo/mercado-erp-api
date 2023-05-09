package com.mercado.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {
	
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private BigDecimal valor;
	private LojaModel loja;

}
