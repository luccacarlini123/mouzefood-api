package com.mouzetech.mouzefood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.api.v1.model.SenhaInput;
import com.mouzetech.mouzefood.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefood.domain.exception.NegocioException;
import com.mouzetech.mouzefood.domain.exception.UsuarioNaoEncontradoException;
import com.mouzetech.mouzefood.domain.model.Usuario;
import com.mouzetech.mouzefood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	private static final String MSG_SENHA_INCONSISTENTE = "A senha atual informada não coincide.";

	private static final String MSG_EMAIL_JA_EXISTENTE = "Já existe um usuário com o email %s";

	private static final String MSG_USUARIO_EM_USO = "Usuário com o id %s não pode ser removido, pois está em uso.";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format(MSG_EMAIL_JA_EXISTENTE, usuario.getEmail()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarPorId(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}
	
	@Transactional
	public void excluir(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
		} catch(EmptyResultDataAccessException ex) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}
	
	@Transactional
	public void alterarSenha(SenhaInput senhaInput, Long usuarioId) {
		Usuario usuario = buscarPorId(usuarioId);
		
		if(usuario.senhaCoincideCom(senhaInput.getSenhaAtual())) {
			throw new NegocioException(MSG_SENHA_INCONSISTENTE);
		}
		
		usuario.setSenha(senhaInput.getNovaSenha());
		
		usuarioRepository.save(usuario);
	}
}