package com.mercado.api.model.funcionario;

import com.mercado.api.model.cargo.CargoResumoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioResumoModel {
	
	private Long id;
	private String nome;
	private CargoResumoModel cargo;
	private Boolean ativo;

}
