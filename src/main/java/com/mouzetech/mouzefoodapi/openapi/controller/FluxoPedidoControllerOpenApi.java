package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation(value = "Confirma um pedido")
	public ResponseEntity<Void> confirmarPedido(@ApiParam(value = "Código de um pedido") String pedidoCodigo);
	
	@ApiOperation(value = "Cancela um pedido")
	public ResponseEntity<Void> cancelarPedido(@ApiParam(value = "Código de um pedido") String pedidoCodigo);
	
	@ApiOperation(value = "Entrega um pedido")
	public ResponseEntity<Void> entregarPedido(@ApiParam(value = "Código de um pedido") String pedidoCodigo);
	
}
