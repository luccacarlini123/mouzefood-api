package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.PedidoResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PedidosModel")
@Getter
@Setter
public class PedidoModelOpenApi {
	private PedidoEmbeddedModel _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@Data
	@ApiModel("PedidosEmbeddedModel")
	public class PedidoEmbeddedModel {
		private List<PedidoResumoModel> pedidos;
	}
}