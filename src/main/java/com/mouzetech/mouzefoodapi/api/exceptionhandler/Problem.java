package com.mouzetech.mouzefoodapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Builder
@Getter
public class Problem {
	
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://mouzefood.com.br/dados-invalido", position = 5)
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos", position = 10)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 15)
	private String detail;
	
	@ApiModelProperty(example = "Há campos que estão inválidos, corrija-os e tente novamente.", position = 20)
	private String userMessage;
	
	@ApiModelProperty(example = "2022-10-15T20:41:39.1336139Z", position = 25)
	private OffsetDateTime date;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que estão com erro", position = 30)
	private List<Object> problemObjects;
	
	@ApiModel("Objeto problema")
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "estado.id")
		private String objectName;
		
		@ApiModelProperty(example = "O id do estado é obrigatório(a)")
		private String userMessage;
	}
	
}
