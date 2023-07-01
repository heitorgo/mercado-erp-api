package com.mercado.api.model.funcionario;

import com.mercado.api.model.cargo.CargoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioModel {
	
	private Long id;
	private String nome;
	private CargoModel cargo;
	private Boolean ativo;

}
