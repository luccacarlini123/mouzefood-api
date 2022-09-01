package com.mouzetech.mouzefoodapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;
import com.mouzetech.mouzefoodapi.domain.model.Produto;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, CatalogoProdutoFotoQueries {

	@Query(value = "from Produto p where p.id = :produtoId and p.restaurante.id = :restauranteId")
	Optional<Produto> buscarProdutoDeUmRestaurante(Long restauranteId, Long produtoId);
	
	@Query(value = "from Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> buscarProdutosAtivosDoRestaurante(Restaurante restaurante);
	
	@Query(value = "select fp from FotoProduto fp "
			+ "join fp.produto.restaurante r "
			+ "where fp.produto.id = :produtoId and r.id = :restauranteId")
	Optional<FotoProduto> buscarFotoProduto(Long restauranteId, Long produtoId);
	
}
