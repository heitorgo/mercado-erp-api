package com.mercado.api.assembler.cargo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.cargo.CargoAlteracaoInput;
import com.mercado.api.model.input.cargo.CargoInput;
import com.mercado.domain.model.Cargo;
import com.mercado.domain.model.Loja;

@Component
public class CargoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cargo toDomainModel(CargoInput cargoInput) {
		return modelMapper.map(cargoInput, Cargo.class);
	}
	
	public void copyToDomainObject(CargoAlteracaoInput cargoAlteracaoInput, Cargo cargo) {
		//para evitar erro de alteração de identificador de loja
		cargo.setLoja(new Loja());
		
		modelMapper.map(cargoAlteracaoInput, cargo);
	}

}
