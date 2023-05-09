package com.mercado.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaModel {
	
	private Long id;
	private BigDecimal valor;
	private String descricao;
	private CaixaModel caixa;
	private FuncionarioModel funcionario;

}
