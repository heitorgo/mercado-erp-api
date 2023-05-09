package com.mercado.api.model.input.funcionario;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mercado.api.model.input.cargo.CargoIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioAlteracaoInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Valid
	private CargoIdInput cargo;
	
	@NotNull
	private boolean ativo;

}
