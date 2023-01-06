package com.mouzetech.mouzefood.domain.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefood.domain.exception.PermissaoNaoEncontradaException;
import com.mouzetech.mouzefood.domain.model.Permissao;
import com.mouzetech.mouzefood.domain.repository.PermissaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CadastroPermissaoService {

	private static final String MSG_PERMISSAO_EM_USO = "A permissão com id %s não pode ser excluída, pois está associada a um ou mais grupos.";
	
	private PermissaoRepository permissaoRepository;
		
	public Permissao buscarPorId(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
	@Transactional
    public void excluir(Long permissaoId) {
        try {
        	permissaoRepository.deleteById(permissaoId);
        	permissaoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PermissaoNaoEncontradaException(permissaoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                String.format(MSG_PERMISSAO_EM_USO, permissaoId));
        }
    }
}
