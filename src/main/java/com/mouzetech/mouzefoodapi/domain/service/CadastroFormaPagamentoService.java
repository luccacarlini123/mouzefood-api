package com.mouzetech.mouzefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefoodapi.domain.exception.FormaPagamentoNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;
import com.mouzetech.mouzefoodapi.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	private static final String MSG_ID_INEXISTENTE_FORMA_PAGAMENTO = "Não existe uma forma de pagamento com o id %d";
	private static final String MSG_RESTAURANTE_ASSOCIADO_FORMA_PAGAMENTO = "A forma de pagamento com o id %d está associada a algum restaurante, impossível excluir";
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento buscarPorId(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(String.format(MSG_ID_INEXISTENTE_FORMA_PAGAMENTO, formaPagamentoId)));
	}
	
	@Transactional
	public void excluir(Long formaPagamentoId) {
		try {			
			FormaPagamento formaPagamento = buscarPorId(formaPagamentoId);
			formaPagamentoRepository.delete(formaPagamento);
			formaPagamentoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_ASSOCIADO_FORMA_PAGAMENTO, formaPagamentoId));
		}
	}
	
}
