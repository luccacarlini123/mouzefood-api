package com.mouzetech.mouzefood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.domain.model.Grupo;
import com.mouzetech.mouzefood.domain.model.Usuario;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrupoUsuarioService {
	
	private CadastroGrupoService cadastroGrupoService;
	private CadastroUsuarioService cadastroUsuarioService;
	
	public List<Grupo> buscarGruposDeUmUsuario(Long usuarioId) {
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		return usuario.getGrupos()
				.stream()
					.collect(Collectors.toList());
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		
		usuario.associarGrupo(grupo);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		
		usuario.desassociarGrupo(grupo);
	}
}