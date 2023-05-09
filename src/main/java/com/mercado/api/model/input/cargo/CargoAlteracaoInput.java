package com.mercado.api.model.input.cargo;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.mercado.api.model.input.loja.LojaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoAlteracaoInput {
	
	@NotBlank
	private String titulo;
	
	@Positive
	@NotNull
	private BigDecimal remuneracao;
	
	@NotNull
	@Valid
	private LojaIdInput loja;
	
	@NotNull
	private boolean ativo;

}
