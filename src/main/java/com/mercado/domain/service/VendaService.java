package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.VendaNaoEncontradaException;
import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.model.Venda;
import com.mercado.domain.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	private static final String msg_venda_em_uso="A venda de codigo identificador %d está em uso";
	
	public Venda salvar(Venda venda) {
		Long caixaId = venda.getCaixa().getId();
		Long funcionarioId = venda.getFuncionario().getId();
		Caixa caixa = caixaService.buscarOuFalhar(caixaId);
		Funcionario funcionario = funcionarioService.buscarOuFalhar(funcionarioId);
		venda.setCaixa(caixa);
		venda.setFuncionario(funcionario);
		return vendaRepository.save(venda);
	}
	
	public void excluir(Long id) {
		try {
			vendaRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new VendaNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_venda_em_uso, id));
		}
	}
	
	public Venda buscarOuFalhar(Long id) {
		return vendaRepository.findById(id).orElseThrow(() ->
		new VendaNaoEncontradaException(id));
	}

}
