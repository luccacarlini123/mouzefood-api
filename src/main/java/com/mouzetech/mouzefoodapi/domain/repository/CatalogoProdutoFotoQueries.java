package com.mouzetech.mouzefoodapi.domain.repository;

import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;

public interface CatalogoProdutoFotoQueries {

	FotoProduto salvarFoto(FotoProduto foto);
	
	void excluirFoto(FotoProduto foto);
	
}