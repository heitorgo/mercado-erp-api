package com.mercado.api.model.input.empresa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaAlteracaoInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String razaoSocial;
	
	@NotNull
	private boolean ativo;

}
