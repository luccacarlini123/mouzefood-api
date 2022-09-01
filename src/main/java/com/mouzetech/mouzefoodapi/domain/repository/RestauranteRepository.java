package com.mouzetech.mouzefoodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteQueries, JpaSpecificationExecutor<Restaurante> {
	
//	@Query("from Restaurante where nome like %:nome% and taxaFrete between :taxaInicial and :taxaFinal")
//	List<Restaurante> buscarPorNomeEntreTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> buscarPorCozinhaIdEntreTaxaFrete(Long cozinhaId, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
}