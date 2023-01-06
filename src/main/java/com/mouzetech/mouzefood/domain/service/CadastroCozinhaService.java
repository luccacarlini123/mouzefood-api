package com.mouzetech.mouzefood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.domain.exception.CozinhaNaoEncontradaException;
import com.mouzetech.mouzefood.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefood.domain.model.Cozinha;
import com.mouzetech.mouzefood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_ASSOCIADA = "A cozinha com id %d está associada a algum restaurante, impossível excluir";
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe nenhuma cozinha com o id %d";
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@Transactional
	public void remover(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_ASSOCIADA, cozinhaId));
		}
	}
	
	public Cozinha buscarPorId(Long cozinhaId) {
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
		return cozinha;
	}
	
}
