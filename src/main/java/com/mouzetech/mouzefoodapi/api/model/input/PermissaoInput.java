package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {
	
	@ApiModelProperty(example = "Excluir Restaurante", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "Essa permissão é para excluir um Restaurante, apenas admins podem fazer uso dela", required = true)
	@NotBlank
	private String descricao;
}
