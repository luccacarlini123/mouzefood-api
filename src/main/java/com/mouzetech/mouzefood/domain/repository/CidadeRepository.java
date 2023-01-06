package com.mouzetech.mouzefood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mouzetech.mouzefood.domain.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Query("from Cidade c join c.estado where c.estado.id = :id")
	List<Cidade> buscarPorEstadoId(Long id);
}