package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UsuarioInputComSenha extends UsuarioInput {
	
	@ApiModelProperty(example = "123", required = true)
	@NotBlank
	private String senha;
}