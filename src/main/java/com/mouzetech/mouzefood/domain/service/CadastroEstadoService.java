package com.mouzetech.mouzefood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.api.v1.assembler.EstadoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.model.EstadoInput;
import com.mouzetech.mouzefood.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefood.domain.exception.EstadoNaoEncontradoException;
import com.mouzetech.mouzefood.domain.model.Estado;
import com.mouzetech.mouzefood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	public Estado buscarPorId(Long estadoId) {
		Estado estado = estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(String.format("Não foi encontrado nenhum Estado com o id %d", estadoId)));
		return estado;
	}
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	@Transactional
	public void excluir(Long id) {
		Estado estado = buscarPorId(id);
		try {			
			estadoRepository.delete(estado);
			estadoRepository.flush();
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Não é possível excluir um Estado com Cidade associada");
		}
	}
	
	public Estado atualizar(EstadoInput estadoInput, Long estadoId) {
		Estado estadoNovo = buscarPorId(estadoId);

		estadoInputDisassembler.copyToObjectDomain(estadoInput, estadoNovo);
		
		return salvar(estadoNovo);
	}
	
}
