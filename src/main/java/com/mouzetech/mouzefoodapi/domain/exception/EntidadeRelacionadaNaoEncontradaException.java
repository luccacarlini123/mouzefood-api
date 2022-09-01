package com.mouzetech.mouzefoodapi.domain.exception;

public class EntidadeRelacionadaNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeRelacionadaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
}
