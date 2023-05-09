package com.mercado.api.model.input.loja;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaIdInput {
	
	@NotNull
	private Long id;

}
