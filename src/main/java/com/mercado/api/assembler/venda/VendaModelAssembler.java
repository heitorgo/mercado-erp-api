package com.mercado.api.assembler.venda;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.VendaModel;
import com.mercado.domain.model.Venda;

@Component
public class VendaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public VendaModel toModel(Venda venda) {
		return modelMapper.map(venda, VendaModel.class);
	}
	
	public List<VendaModel> toCollectionModel(List<Venda> vendas){
		return vendas.stream()
				.map(venda -> toModel(venda))
				.collect(Collectors.toList());
	}

}
