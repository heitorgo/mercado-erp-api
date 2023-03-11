package com.mercado.domain.exception;

public class CaixaNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public CaixaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CaixaNaoEncontradoException(Long id) {
		super(String.format("Caixa de código identificador %d não foi encontrado", id));
	}

}
