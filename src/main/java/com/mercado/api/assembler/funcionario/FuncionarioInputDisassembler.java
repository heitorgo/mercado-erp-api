package com.mercado.api.assembler.funcionario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.funcionario.FuncionarioAlteracaoInput;
import com.mercado.api.model.input.funcionario.FuncionarioInput;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.model.Funcionario;

@Component
public class FuncionarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Funcionario toDomainModel(FuncionarioInput funcionarioInput) {
		return modelMapper.map(funcionarioInput, Funcionario.class);
	}
	
	public void copyToDomainObject(FuncionarioAlteracaoInput funcionarioAlteracaoInput, Funcionario funcionario) {
		//para evitar erro de alteração de identificador de cargo
		funcionario.setCargo(new Cargo());
		
		modelMapper.map(funcionarioAlteracaoInput, funcionario);
	}

}
