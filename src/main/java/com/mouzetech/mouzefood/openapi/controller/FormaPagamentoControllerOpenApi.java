package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoInput;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;
import com.mouzetech.mouzefood.openapi.model.FormasPagamentoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "FormasPagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Buscar todas as formas de pagamento")
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)
			})
	ResponseEntity<CollectionModel<FormaPagamentoModel>> buscar(ServletWebRequest request);
	
	@ApiOperation(value = "Buscar forma de pagamento por ID")
	ResponseEntity<FormaPagamentoModel> buscarPorId(
			@ApiParam(value = "ID da forma de pagamento", example = "2")
			Long formaPagamentoId, ServletWebRequest request);
	
	@ApiOperation(value = "Salvar forma de pagamento")
	ResponseEntity<FormaPagamentoModel> salvar(
			@ApiParam(value = "Representação de uma nova forma de pagamento", name = "corpo")
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento atualizada com sucesso")
	})
	@ApiOperation(value = "Atualizar forma de pagamento")
	void atualizar(
			@ApiParam(value = "ID da forma de pagamento", example = "2")
			Long formaPagamentoId, 
			
			@ApiParam(value = "Representação de uma forma de pagamento a ser atualizada", name = "corpo")
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluída com sucesso")
	})
	@ApiOperation(value = "Excluir forma de pagamento")
	void excluir(
			@ApiParam(value = "ID da forma de pagamento", example = "2") 
			Long formaPagamentoId);
	
}
