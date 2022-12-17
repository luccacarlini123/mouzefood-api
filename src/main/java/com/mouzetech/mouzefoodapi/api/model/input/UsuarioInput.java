package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UsuarioInput {

	@ApiModelProperty(example = "Lucca Carlini", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "lucca.carlini@gmail.com", required = true)
	@NotBlank
	@Email
	private String email;
}