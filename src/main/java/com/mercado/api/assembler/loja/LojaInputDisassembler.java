package com.mercado.api.assembler.loja;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.loja.LojaAlteracaoInput;
import com.mercado.api.model.input.loja.LojaInput;
import com.mercado.domain.model.Empresa;
import com.mercado.domain.model.Loja;

@Component
public class LojaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Loja toDomainModel(LojaInput lojaInput) {
		return modelMapper.map(lojaInput, Loja.class);
	}
	
	public void copyToDomainObject(LojaAlteracaoInput lojaAlteracaoInput, Loja loja) {
		//para evitar erro de alteração de identificador de empresa
		loja.setEmpresa(new Empresa());
		
		modelMapper.map(lojaAlteracaoInput, loja);
	}

}
