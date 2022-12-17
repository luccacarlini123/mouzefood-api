package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CozinhaInput {

	@ApiModelProperty(value = "Nome da cozinha", example = "Italiana")
	@NotBlank
	private String nome;
}