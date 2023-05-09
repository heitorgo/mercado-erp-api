package com.mercado.api.model.input.formaPagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoAlteracaoInput {
	
	@NotBlank
	private String titulo;
	
	@NotNull
	private boolean ativo;

}
