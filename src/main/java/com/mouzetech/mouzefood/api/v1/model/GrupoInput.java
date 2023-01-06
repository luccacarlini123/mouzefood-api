package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GrupoInput {

	@ApiModelProperty(value = "Nome do grupo", required = true, example = "Gerente")
	@NotBlank
	private String nome;
}