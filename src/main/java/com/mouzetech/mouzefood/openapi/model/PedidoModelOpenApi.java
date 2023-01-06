package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefood.api.v1.model.PedidoResumoModel;

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