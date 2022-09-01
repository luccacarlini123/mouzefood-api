package com.mouzetech.mouzefoodapi.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.api.model.disassembler.RestauranteInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.RestauranteInput;
import com.mouzetech.mouzefoodapi.domain.exception.CidadeNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.exception.CozinhaNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.exception.NegocioException;
import com.mouzetech.mouzefoodapi.domain.exception.RestauranteNaoEncontradoException;
import com.mouzetech.mouzefoodapi.domain.model.Cidade;
import com.mouzetech.mouzefoodapi.domain.model.Cozinha;
import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	public Restaurante buscarPorId(Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		if(restaurante.isEmpty()) {
			throw new RestauranteNaoEncontradoException(String.format("Não existe nenhum Restaurante com o id %d", id));
		}
		
		return restaurante.get();
	}
	
	public Restaurante salvar(Restaurante restaurante) {
		
		try {			
			Long cozinhaId = restaurante.getCozinha().getId();
			Long cidadeId = restaurante.getEndereco().getCidade().getId();
			
			Cozinha cozinha = cadastroCozinhaService.buscarPorId(cozinhaId);
			Cidade cidade = cadastroCidadeService.buscarPorId(cidadeId);
			
			restaurante.setCozinha(cozinha);
			restaurante.getEndereco().setCidade(cidade);
			
			return restauranteRepository.save(restaurante);
		}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarPorId(formaPagamentoId);
		
		restaurante.desassociarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarPorId(formaPagamentoId);
		
		restaurante.associarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void ativarRestaurantes(List<Long> restauranteIds) {
		restauranteIds.forEach(this::ativar);
	}
	
	@Transactional
	public void desativarRestaurantes(List<Long> restauranteIds) {
		restauranteIds.forEach(this::desativar);
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarPorId(restauranteId);
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void desativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarPorId(restauranteId);
		restauranteAtual.desativar();
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		restaurante.fechar();
	}
	
	public Restaurante atualizar(RestauranteInput restauranteInput, Long restauranteId) {
		Restaurante restauranteAtual = buscarPorId(restauranteId);

		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
		return salvar(restauranteAtual);
	}
	
}
