package com.mouzetech.mouzefoodapi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.exception.NegocioException;
import com.mouzetech.mouzefoodapi.domain.exception.PedidoNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.model.Cidade;
import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;
import com.mouzetech.mouzefoodapi.domain.model.Pedido;
import com.mouzetech.mouzefoodapi.domain.model.Produto;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.model.Usuario;
import com.mouzetech.mouzefoodapi.domain.repository.PedidoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmissaoPedidoService {

	private PedidoRepository pedidoRepository;
	private CadastroRestauranteService cadastroRestauranteService;
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private CadastroProdutoService cadastroProdutoService;
	private CadastroUsuarioService cadastroUsuarioService;
	private CadastroCidadeService cadastroCidadeService;
	
	public Pedido buscarPorCodigo(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradaException("Não foi encontrado nenhum pedido com o código: "+ codigo));
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);

		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}

	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarPorId(pedido.getFormaPagamento().getId());
		Usuario usuario = cadastroUsuarioService.buscarPorId(pedido.getCliente().getId());
		Cidade cidade = cadastroCidadeService.buscarPorId(pedido.getEndereco().getCidade().getId());
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("O restaurante %s, não aceita a forma de pagamento %s.", restaurante.getNome(), formaPagamento.getDescricao()));
		}
		
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.setCliente(usuario);
		pedido.getEndereco().setCidade(cidade);
	}
	
	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(
						pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
}