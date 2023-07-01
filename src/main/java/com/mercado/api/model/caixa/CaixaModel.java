package com.mercado.api.model.caixa;

import com.mercado.api.model.loja.LojaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaModel {
	
	private Long id;
	private String nome;
	private LojaModel loja;
	private Boolean ativo;

}
