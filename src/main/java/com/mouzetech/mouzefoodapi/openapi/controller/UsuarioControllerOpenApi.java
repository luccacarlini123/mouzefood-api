package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefoodapi.api.model.input.SenhaInput;
import com.mouzetech.mouzefoodapi.api.model.input.UsuarioInput;
import com.mouzetech.mouzefoodapi.api.model.input.UsuarioInputComSenha;
import com.mouzetech.mouzefoodapi.api.model.output.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation(value = "Busca todos os usuários")
	public CollectionModel<UsuarioModel> buscarTodos();
	
	@ApiOperation(value = "Busca usuário por id")
	public ResponseEntity<UsuarioModel> buscarPorId(
			@ApiParam(value = "ID do usuário", required = true) 
			Long usuarioId);
	
	@ApiOperation(value = "Salva um usuário com senha")
	public ResponseEntity<UsuarioModel> salvar(
			@ApiParam(value = "Novo usuário a ser salvo", required = true, name = "corpo")
			UsuarioInputComSenha usuarioInput);
	
	@ApiOperation(value = "Atualiza um usuário")
	public ResponseEntity<UsuarioModel> atualizar(
			@ApiParam(value = "ID do usuário", required = true) 
			Long usuarioId, 
			
			@ApiParam(value = "Usuário sem senha a ser atualizado", required = true, name = "corpe")
			UsuarioInput usuarioSemSenhaInput);
	
	@ApiOperation(value = "Exclui um usuário")
	public void excluir(
			@ApiParam(value = "ID do usuário", required = true)
			Long usuarioId);
	
	@ApiOperation(value = "Altera a senha de um usuário")
	public void alterarSenha(
			@ApiParam(value = "ID do usuário", required = true)
			Long usuarioId, 
			
			@ApiParam(value = "Senhas do usuário para ser atualizada", required = true, name = "corpo")
			SenhaInput senhaInput);
	
}
