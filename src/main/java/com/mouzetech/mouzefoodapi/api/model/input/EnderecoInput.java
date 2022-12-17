package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EnderecoInput {

	@ApiModelProperty(example = "26600000")
	private String cep;
	
	@ApiModelProperty(example = "Rua Feliciana dos Anjos Teixeira", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "582", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "Próximo ao antigo casarão")
	private String complemento;
	
	@ApiModelProperty(example = "Sabugo", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;
}