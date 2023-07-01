package com.mercado.api.assembler.usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.usuario.UsuarioInput;
import com.mercado.domain.model.Usuario;

@Component
public class UsuarioInputDisassember {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainModel(UsuarioInput usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}

}
