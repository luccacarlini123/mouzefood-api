package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.EstadoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosModel")
@Setter
@Getter
public class EstadoModelOpenApi {

	private EstadosEmbbededOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("EstadosEmbbededModel")
	public class EstadosEmbbededOpenApi {
		private List<EstadoModel> estados;
	}
	
}
