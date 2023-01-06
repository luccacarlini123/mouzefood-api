package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EstadoInput {
	
	@ApiModelProperty(example = "Rio de Janeiro", required = true)
	@NotBlank
	private String nome;
}