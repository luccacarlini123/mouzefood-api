package com.mouzetech.mouzefood.domain.repository;

import com.mouzetech.mouzefood.domain.model.FotoProduto;

public interface CatalogoProdutoFotoQueries {

	FotoProduto salvarFoto(FotoProduto foto);
	
	void excluirFoto(FotoProduto foto);
	
}