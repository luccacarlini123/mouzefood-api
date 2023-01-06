package com.mouzetech.mouzefood.openapi.model;

import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProblemaNotFoundOpenApi")
@Getter
@Setter
public class ProblemaNotFoundOpenApi {

	@ApiModelProperty(example = "404", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://mouzefood.com.br/entidade-nao-encontrada", position = 5)
	private String type;
	
	@ApiModelProperty(example = "Entidade não encontrada", position = 10)
	private String title;
	
	@ApiModelProperty(example = "Não existe nenhum Restaurante com o id 102", position = 15)
	private String detail;
	
	@ApiModelProperty(example = "2022-10-25T21:56:00.7067691Z", position = 25)
	private OffsetDateTime date;
	
}
