package com.mouzetech.mouzefoodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

public interface RestauranteQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findComFreteGratis(String nome);

}