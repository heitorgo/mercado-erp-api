package com.mercado.api.model.venda;

import java.math.BigDecimal;
import java.util.List;

import com.mercado.api.model.formaPagamento.FormaPagamentoModel;
import com.mercado.api.model.funcionario.CargoFuncionarioModel;
import com.mercado.api.model.itemVenda.ItemVendaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaVendaModel {
	
	private Long id;
	private BigDecimal valor;
	private String descricao;
	private CargoFuncionarioModel funcionario;
	private FormaPagamentoModel formaPagamento;
	private List<ItemVendaModel> itens;
}
