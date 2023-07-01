package com.mercado.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercado.api.model.input.itemVenda.ItemVendaInput;
import com.mercado.domain.model.ItemVenda;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
		modelMapper.createTypeMap(ItemVendaInput.class, ItemVenda.class)
			.addMappings(mapper -> mapper.skip(ItemVenda::setId));
		
		return modelMapper;
	}

}
