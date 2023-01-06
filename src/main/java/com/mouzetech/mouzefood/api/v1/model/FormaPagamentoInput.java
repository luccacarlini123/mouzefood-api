package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FormaPagamentoInput {

	@ApiModelProperty(value = "Descrição de uma forma de pagamento", example = "Dinheiro", required = true)
	@NotBlank
	private String descricao;
}
