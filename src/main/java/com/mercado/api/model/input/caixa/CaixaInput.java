package com.mercado.api.model.input.caixa;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.mercado.api.model.input.loja.LojaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaInput {
	
	@NotBlank
	@NotNull
	private String nome;
	
	@PositiveOrZero
	@NotNull
	private BigDecimal saldo;
	
	@NotNull
	@Valid
	private LojaIdInput loja;

}
