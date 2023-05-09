package com.mercado.api.model.input.caixa;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mercado.api.model.input.loja.LojaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaAlteracaoInput {
	
	@NotBlank
	@NotNull
	private String nome;
	
	@NotNull
	@Valid
	private LojaIdInput loja;
	
	@NotNull
	private boolean ativo;

}
