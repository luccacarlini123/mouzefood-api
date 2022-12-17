package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModelOpenApi {

	@ApiModelProperty(value = "Número da página", example = "0")
	private int page;
	
	@ApiModelProperty(value = "Tamanho do número de elementos por página", example = "3")
	private int size;
	
	@ApiModelProperty(value = "Lista de campos para poder ordenar", example = "nome,asc")
	private List<String> sort;
	
}
