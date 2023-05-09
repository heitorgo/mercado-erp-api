package com.mercado.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioModel {
	
	private Long id;
	private String nome;
	private CargoModel cargo;

}
