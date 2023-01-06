package com.mouzetech.mouzefood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.assembler.UsuarioInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.UsuarioModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.SenhaInput;
import com.mouzetech.mouzefood.api.v1.model.UsuarioInput;
import com.mouzetech.mouzefood.api.v1.model.UsuarioInputComSenha;
import com.mouzetech.mouzefood.api.v1.model.UsuarioModel;
import com.mouzetech.mouzefood.domain.model.Usuario;
import com.mouzetech.mouzefood.domain.repository.UsuarioRepository;
import com.mouzetech.mouzefood.domain.service.CadastroUsuarioService;
import com.mouzetech.mouzefood.openapi.controller.UsuarioControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController implements UsuarioControllerOpenApi {

	private UsuarioRepository usuarioRepository;
	private CadastroUsuarioService cadastroUsuarioService;
	private UsuarioModelAssembler usuarioModelAssembler;
	private UsuarioInputDisassembler usuarioInputDisassembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioModel> buscarTodos(){
		return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable Long usuarioId){
		return ResponseEntity.ok(usuarioModelAssembler.toModel(cadastroUsuarioService.buscarPorId(usuarioId)));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioModel> salvar(@RequestBody @Valid UsuarioInputComSenha usuarioInput){
		Usuario usuario = usuarioInputDisassembler.toObject(usuarioInput);
		usuario = cadastroUsuarioService.salvar(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelAssembler.toModel(usuario));
	}
	
	@PutMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioModel> atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioSemSenhaInput) {
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		usuarioInputDisassembler.copyToDomainObject(usuarioSemSenhaInput, usuario);
		return ResponseEntity.ok(
				usuarioModelAssembler.toModel(
						cadastroUsuarioService.salvar(usuario)));
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long usuarioId) {
		cadastroUsuarioService.excluir(usuarioId);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
		cadastroUsuarioService.alterarSenha(senhaInput, usuarioId);
	}
}