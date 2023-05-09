package com.mercado.api.model.input.venda;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.mercado.api.model.input.caixa.CaixaIdInput;
import com.mercado.api.model.input.funcionario.FuncionarioIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaInput {
	
	@NotNull
	@PositiveOrZero
	private BigDecimal valor;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	@Valid
	private CaixaIdInput caixa;

	@Valid
	private  FuncionarioIdInput funcionario;
	
}
