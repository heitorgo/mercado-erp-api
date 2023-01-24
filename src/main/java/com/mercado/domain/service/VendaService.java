package com.mercado.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mercado.domain.exception.EntidadeEmUsoException;
import com.mercado.domain.exception.EntidadeNaoEncontradaException;
import com.mercado.domain.model.Caixa;
import com.mercado.domain.model.Funcionario;
import com.mercado.domain.model.Venda;
import com.mercado.domain.repository.CaixaRepository;
import com.mercado.domain.repository.FuncionarioRepository;
import com.mercado.domain.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private CaixaRepository caixaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public Venda salvar(Venda venda) {
		Long caixaId = venda.getCaixa().getId();
		Long funcionarioId = venda.getFuncionario().getId();
		Caixa caixa = caixaRepository.findById(caixaId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(
						"Não existe nenhum caixa de codigo identificador %d", caixaId)));
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(
						"Não existe nenhum funcionario de codigo identificador %d", funcionarioId)));
		venda.setCaixa(caixa);
		venda.setFuncionario(funcionario);
		return vendaRepository.save(venda);
	}
	
	public void excluir(Long id) {
		try {
			vendaRepository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A venda de codigo identificador %d está em uso", id));
		}
	}

}
