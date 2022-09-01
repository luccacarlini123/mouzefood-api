package com.mouzetech.mouzefoodapi.domain.repository;

import java.util.List;

import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

public interface CozinhaQueries {

	List<Restaurante> buscarRestaurantes(Long cozinhaId);
	
}
