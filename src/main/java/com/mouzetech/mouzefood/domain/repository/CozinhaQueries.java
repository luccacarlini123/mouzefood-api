package com.mouzetech.mouzefood.domain.repository;

import java.util.List;

import com.mouzetech.mouzefood.domain.model.Restaurante;

public interface CozinhaQueries {

	List<Restaurante> buscarRestaurantes(Long cozinhaId);
	
}
