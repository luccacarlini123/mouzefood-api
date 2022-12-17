package com.mouzetech.mouzefoodapi.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	@Query("select max(fp.dataAtualizacao) from FormaPagamento fp")
	public OffsetDateTime obterMaiorDataAtualizacao();
	
	@Query("select max(fp.dataAtualizacao) from FormaPagamento fp where fp.id = :id")
	public OffsetDateTime obterMaiorDataAtualizacaoPorId(Long id);
	
}