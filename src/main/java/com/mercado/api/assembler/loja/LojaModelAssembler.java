package com.mercado.api.assembler.loja;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.loja.LojaModel;
import com.mercado.domain.model.Loja;

@Component
public class LojaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public LojaModel toModel(Loja loja) {
		return modelMapper.map(loja, LojaModel.class);
	}
	
	public List<LojaModel> toCollectionModel(List<Loja> lojas){
		return lojas.stream()
				.map(loja -> toModel(loja))
				.collect(Collectors.toList());
	}

}
