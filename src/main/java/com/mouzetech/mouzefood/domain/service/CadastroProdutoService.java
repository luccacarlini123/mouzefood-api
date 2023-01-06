package com.mouzetech.mouzefood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouzetech.mouzefood.domain.exception.ProdutoNaoEncontradoException;
import com.mouzetech.mouzefood.domain.model.Produto;
import com.mouzetech.mouzefood.domain.model.Restaurante;
import com.mouzetech.mouzefood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	private static final String MSG_PRODUTO_NAO_PERTENCE_RESTAURANTE = "Não foi possível encontrar um produto de id %s que pertença a um restaurante que possui o id %s";

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	public Produto buscarPorId(Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		if(produto.isEmpty()) {
			throw new ProdutoNaoEncontradoException(String.format("Produto não encontrado com esse id %d", id));
		}
		return produto.get();
	}
	
	public List<Produto> buscarProdutosAtivosDoRestaurante(Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		return produtoRepository.buscarProdutosAtivosDoRestaurante(restaurante);
	}
	
	public List<Produto> buscarProdutosDoRestaurante(Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		return restaurante.getProdutos();
	}
	
	public Produto buscarProdutoDoRestaurante(Long restauranteId, Long produtoId) {
		Optional<Produto> optionalProduto = produtoRepository.buscarProdutoDeUmRestaurante(restauranteId, produtoId);
		
		if(optionalProduto.isEmpty()) {
			throw new ProdutoNaoEncontradoException(
					String.format(MSG_PRODUTO_NAO_PERTENCE_RESTAURANTE, produtoId, restauranteId));
		}
		
		return optionalProduto.get();
	}
	
	public Produto adicionarProdutoEmRestaurante(Long restauranteId, Produto produto) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		produto.setRestaurante(restaurante);
		
		return produtoRepository.save(produto);
	}
	
}
