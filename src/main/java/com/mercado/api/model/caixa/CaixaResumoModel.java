package com.mercado.api.model.caixa;

import com.mercado.api.model.loja.LojaResumoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaResumoModel {
	
	private Long id;
	private String nome;
	private LojaResumoModel loja;
	private Boolean ativo;

}
