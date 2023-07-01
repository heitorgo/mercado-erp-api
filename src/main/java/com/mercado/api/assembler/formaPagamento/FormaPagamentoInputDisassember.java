package com.mercado.api.assembler.formaPagamento;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.formaPagamento.FormaPagamentoInput;
import com.mercado.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassember {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainModel(FormaPagamentoInput formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}

}
