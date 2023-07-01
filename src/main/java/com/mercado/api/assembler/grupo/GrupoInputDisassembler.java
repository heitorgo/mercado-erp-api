package com.mercado.api.assembler.grupo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.grupo.GrupoInput;
import com.mercado.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainModel(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}

}
