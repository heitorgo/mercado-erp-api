package com.mercado.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaModel {
	
	private Long id;
	private String nome;
	private BigDecimal saldo;
	private LojaModel loja;

}
