package com.mouzetech.mouzefoodapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mouzetech.mouzefoodapi.core.validator.PrecoMaiorQueZero;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProdutoInput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
	
	@PrecoMaiorQueZero
	@NotNull
	private BigDecimal preco;
	
	@NotNull
	private Boolean ativo;
}