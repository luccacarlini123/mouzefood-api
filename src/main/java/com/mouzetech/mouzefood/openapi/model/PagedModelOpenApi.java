package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelOpenApi<T> {

	private List<T> content;      
	
	@ApiModelProperty(value = "Quantidade de registros por página", example = "1")
	private Long size;
	
	@ApiModelProperty(value = "Total de registros", example = "3")
	private Long totalElements;
	
	@ApiModelProperty(value = "Total de páginas", example = "3")
	private Long totalPages;
	
	@ApiModelProperty(value = "Número da página (começa em 0)", example = "0")
	private Long number;
	
}