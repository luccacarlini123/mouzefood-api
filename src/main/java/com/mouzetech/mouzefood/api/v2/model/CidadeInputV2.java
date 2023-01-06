package com.mouzetech.mouzefood.api.v2.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CidadeInputV2 {
	
	@ApiModelProperty(example = "Paracambi", required = true)
	@NotBlank
	private String nome;
	
	@NotNull
	private Long estadoId;
}