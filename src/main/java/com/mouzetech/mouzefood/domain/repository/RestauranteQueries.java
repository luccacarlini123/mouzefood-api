package com.mouzetech.mouzefood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mouzetech.mouzefood.domain.model.Restaurante;

public interface RestauranteQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}