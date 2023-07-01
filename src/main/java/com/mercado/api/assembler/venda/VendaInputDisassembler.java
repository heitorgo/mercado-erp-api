package com.mercado.api.assembler.venda;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mercado.api.model.input.venda.CaixaVendaInput;
import com.mercado.api.model.input.venda.VendaInput;
import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.FormaPagamento;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.model.Venda;

@Component
public class VendaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Venda toDomainModel(VendaInput vendaInput) {
		return modelMapper.map(vendaInput, Venda.class);
	}
	
	public Venda toDomainModel(CaixaVendaInput caixaVendaInput) {
		return modelMapper.map(caixaVendaInput, Venda.class);
	}
	
	public void copyToDomainObject(VendaInput vendaInput, Venda venda) {
		//para evitar erro de alteração de identificador de caixa
		venda.setCaixa(new Caixa());
		//para evitar erro de alteração de identificador de funcionario
		venda.setFuncionario(new Funcionario());
		//para evitar erro de alteração de identificador de forma de pagamento
		venda.setFormaPagamento(new FormaPagamento());
		
		modelMapper.map(vendaInput, venda);
	}
	
	public void copyToDomainObject(CaixaVendaInput caixaVendaInput, Venda venda) {
		//para evitar erro de alteração de identificador de funcionario
		venda.setFuncionario(new Funcionario());
		
		//para evitar erro de alteração de identificador de forma de pagamento
		venda.setFormaPagamento(new FormaPagamento());
		
		modelMapper.map(caixaVendaInput, venda);
	}

}
