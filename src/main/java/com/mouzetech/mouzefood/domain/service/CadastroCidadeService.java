package com.mouzetech.mouzefood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.api.v1.assembler.CidadeInputDisassembler;
import com.mouzetech.mouzefood.api.v1.model.CidadeInput;
import com.mouzetech.mouzefood.domain.exception.CidadeNaoEncontradaException;
import com.mouzetech.mouzefood.domain.exception.NegocioException;
import com.mouzetech.mouzefood.domain.model.Cidade;
import com.mouzetech.mouzefood.domain.model.Estado;
import com.mouzetech.mouzefood.domain.repository.CidadeRepository;
import com.mouzetech.mouzefood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Estado estado;
		estado = estadoRepository.findById(cidade.getEstado().getId())
				.orElseThrow(() -> new NegocioException(String.format("Não foi possível encontrar um estado com o id %d", cidade.getEstado().getId())));

		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public Cidade buscarPorId(Long id) {
		Cidade cidade = cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(String.format("Não foi possível encontrar uma cidade com o id %d", id)));
		return cidade;
	}
	
	@Transactional
	public void excluir(Long id) {
		Cidade cidade = buscarPorId(id);
		cidadeRepository.delete(cidade);
	}
	
	public Cidade atualizar(CidadeInput cidadeInput, Long cidadeId) {
		Cidade cidadeAtual = buscarPorId(cidadeId);
		
		cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		
		return salvar(cidadeAtual);
	}
	
	public List<Cidade> buscarPorEstadoId(Long estadoId) {
		List<Cidade> cidades = cidadeRepository.buscarPorEstadoId(estadoId);
		if(cidades.isEmpty()) {
			 throw new CidadeNaoEncontradaException(String.format("Não foi possível encontrar Cidades para esse Estado com o id %d", estadoId));
		}
		return cidades;
	}
	
}
