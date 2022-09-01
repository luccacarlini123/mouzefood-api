package com.mouzetech.mouzefoodapi.domain.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;
import com.mouzetech.mouzefoodapi.domain.repository.CatalogoProdutoFotoQueries;

@Repository
public class ProdutoRepositoryImpl implements CatalogoProdutoFotoQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public FotoProduto salvarFoto(FotoProduto foto) {
		return manager.merge(foto);
	}
	
	@Transactional
	@Override
	public void excluirFoto(FotoProduto foto) {
		manager.remove(foto);
	}
	
}