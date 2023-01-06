package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FormaPagamentoInputId {
	
	@ApiModelProperty(value = "ID da forma de pagamento", example = "1", required = true)
	@NotNull
	private Long id;
}