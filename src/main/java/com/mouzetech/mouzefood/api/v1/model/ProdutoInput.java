package com.mouzetech.mouzefood.api.v1.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mouzetech.mouzefood.core.validator.PrecoMaiorQueZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProdutoInput {

	@ApiModelProperty(example = "Prato feito de contra-filé com fritas", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Arroz, feijão, farofa, vinagrete, contra-filé e batata-frita", required = true)
	@NotBlank
	private String descricao;
	
	@ApiModelProperty(example = "29.99", required = true)
	@PrecoMaiorQueZero
	@NotNull
	private BigDecimal preco;
	
	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private Boolean ativo;
}