package com.mouzetech.mouzefoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel {
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
}