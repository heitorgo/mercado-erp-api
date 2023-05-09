package com.mercado.api.model.input.empresa;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mercado.api.model.input.usuario.UsuarioIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String razaoSocial;
	
	@NotNull
	@Valid
	private UsuarioIdInput usuario;

}
