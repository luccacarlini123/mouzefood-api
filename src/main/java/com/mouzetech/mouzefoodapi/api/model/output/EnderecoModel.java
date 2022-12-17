package com.mouzetech.mouzefoodapi.api.model.output;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {

	@ApiModelProperty(example = "26600000")
	private String cep;
	
	@ApiModelProperty(example = "Rua Feliciana dos Anjos Teixeira")
	private String logradouro;
	
	@ApiModelProperty(example = "582")
	private String numero;
	
	@ApiModelProperty(example = "Próximo ao antigo casarão")
	private String complemento;
	
	@ApiModelProperty(example = "Sabugo")
	private String bairro;
	
	@ApiModelProperty(value = "Representação da cidade")
	private CidadeResumoModel cidade;
}