package com.mouzetech.mouzefoodapi.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.model.Grupo;
import com.mouzetech.mouzefoodapi.domain.model.Permissao;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GrupoPermissaoService {

	private CadastroGrupoService cadastroGrupoService;
	private CadastroPermissaoService cadastroPermissaoService;
	
	public List<Permissao> buscarPermissoesDeUmGrupo(Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		return grupo.getPermissoes()
				.stream()
					.collect(Collectors.toList());
	}	
	
	@Transactional
	public void associarPermissaoAoGrupo(Long grupoId, Long permissaoId) {
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarPorId(permissaoId);
		
		grupo.associarPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissaoAoGrupo(Long grupoId, Long permissaoId) {
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		Permissao permissao = cadastroPermissaoService.buscarPorId(permissaoId);
		
		grupo.desassociarPermissao(permissao);
	}
}
