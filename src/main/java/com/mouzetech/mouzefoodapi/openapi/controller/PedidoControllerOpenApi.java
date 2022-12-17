package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.mouzetech.mouzefoodapi.api.model.input.PedidoInput;
import com.mouzetech.mouzefoodapi.api.model.output.PedidoModel;
import com.mouzetech.mouzefoodapi.api.model.output.PedidoResumoModel;
import com.mouzetech.mouzefoodapi.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	@ApiOperation(value = "Buscar pedidos usando filtro")
	PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

	
	@ApiOperation(value = "Buscar pedidos por código")
	PedidoModel buscarPorId(
			@ApiParam(value = "Código de um pedido", example = "c6ac0f9b-0869-4f8f-8444-c2363a7afb65")
			String pedidoCodigo);
	
	@ApiOperation(value = "Emitir um pedido")
	PedidoModel emitirPedido(
			@ApiParam(value = "Representação de um pedido a ser emitido", name = "corpo")
			PedidoInput pedidoInput);
	
}
