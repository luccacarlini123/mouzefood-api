package com.mouzetech.mouzefoodapi.domain.infrastructure.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.mouzetech.mouzefoodapi.domain.model.Cozinha;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaQueries;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCozinhaService;

@Repository
public class CozinhaRepositoryImpl implements CozinhaQueries{

	@Autowired @Lazy
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Override
	public List<Restaurante> buscarRestaurantes(Long cozinhaId) {
		Cozinha cozinha = cadastroCozinhaService.buscarPorId(cozinhaId);
		return cozinha.getRestaurantes();
	}

}
