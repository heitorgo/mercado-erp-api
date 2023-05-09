package com.mercado.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaModel {
	
	private Long id;
	private String nome;
	private BigDecimal saldo;
	private EmpresaModel empresa;

}
