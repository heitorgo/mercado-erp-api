package com.mercado.api.assembler.caixa;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.CaixaModel;
import com.mercado.domain.model.Caixa;

@Component
public class CaixaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CaixaModel toModel(Caixa caixa) {
		return modelMapper.map(caixa, CaixaModel.class);
	}
	
	public List<CaixaModel> toCollectionModel(List<Caixa> caixas){
		return caixas.stream()
				.map(caixa -> toModel(caixa))
				.collect(Collectors.toList());
	}

}
