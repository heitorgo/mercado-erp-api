package com.mercado.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoModel {
	
	private Long id;
	
	private String titulo;
	
	private BigDecimal remuneracao;
	
	private LojaModel loja;

}
