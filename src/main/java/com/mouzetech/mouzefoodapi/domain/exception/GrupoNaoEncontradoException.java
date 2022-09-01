package com.mouzetech.mouzefoodapi.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoNaoEncontradoException(Long grupoId) {
		super(String.format("O grupo com o id %d não existe.", grupoId));
	}
	
}
