package com.mercado.domain.exception;

public class CargoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;
	
	public CargoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public CargoNaoEncontradoException(Long id) {
		super(String.format("Cargo de código identificador %d não foi encontrado", id));
	}

}
