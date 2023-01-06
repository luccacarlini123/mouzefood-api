package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Restaurantes")
public interface RestauranteFormasPagamentoOpenApi {

	@ApiOperation(value = "Busca formas de pagamento de um restaurante pelo ID do restaurante")
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> buscarFormasPagamento(
			@ApiParam(value = "ID do restaurante", required = true) 
			Long restauranteId);
	
	@ApiOperation(value = "Associa forma de pagamento a um restaurante")
	public ResponseEntity<Void> associarFormaPagamento(
			@ApiParam(value = "ID do restaurante", required = true)
			Long restauranteId, 
			
			@ApiParam(value = "ID da forma de pagamento", required = true)
			Long formaPagamentoId);
	
	@ApiOperation(value = "Desassocia uma forma de pagamento de um restaurante")
	public ResponseEntity<Void> desassociarFormaPagamento(
			@ApiParam(value = "ID do restaurante") 
			Long restauranteId, 

			@ApiParam(value = "ID da forma de pagamento", required = true)
			Long formaPagamentoId);
	
}
