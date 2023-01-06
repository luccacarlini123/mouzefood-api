package com.mouzetech.mouzefood.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.domain.model.Restaurante;
import com.mouzetech.mouzefood.domain.model.Usuario;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestauranteUsuarioResponsavelService {

	private CadastroRestauranteService cadastroRestauranteService;
	private CadastroUsuarioService cadastroUsuarioService;
	
	public List<Usuario> buscarUsuariosResponsaveisDoRestaurante(Long restauranteId) {
		return cadastroRestauranteService
				.buscarPorId(restauranteId).getResponsaveis()
					.stream()
						.collect(Collectors.toList());
	}
	
	@Transactional
	public void associarResponsavelAoRestaurante(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		
		restaurante.adicionarResponsavel(usuario);
	}
	
	@Transactional
	public void desassociarResponsavelAoRestaurante(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		
		restaurante.removerResponsavel(usuario);
	}
}