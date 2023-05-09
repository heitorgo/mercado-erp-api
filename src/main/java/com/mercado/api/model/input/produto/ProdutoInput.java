package com.mercado.api.model.input.produto;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.mercado.api.model.input.loja.LojaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@PositiveOrZero
	@NotNull
	private Integer quantidade;
	
	@Positive
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@Valid
	private LojaIdInput loja;

}
