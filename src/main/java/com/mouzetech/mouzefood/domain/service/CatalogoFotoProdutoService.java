package com.mouzetech.mouzefood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefood.domain.exception.FotoProdutoNaoEncontradaException;
import com.mouzetech.mouzefood.domain.model.FotoProduto;
import com.mouzetech.mouzefood.domain.repository.ProdutoRepository;
import com.mouzetech.mouzefood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	@Transactional
	public FotoProduto salvarFoto(FotoProduto foto, InputStream inputStream) {
		Long restauranteId = foto.getRestauranteId();
		Long produtoId = foto.getProduto().getId();
		String nomeArquivoExistente = null;
		
		Optional<FotoProduto> optionalFotoProduto = 
				produtoRepository.buscarFotoProduto(restauranteId, produtoId);
		
		if(optionalFotoProduto.isPresent()) {
			nomeArquivoExistente = optionalFotoProduto.get().getNomeArquivo();
			produtoRepository.excluirFoto(optionalFotoProduto.get());
		}
		
		String novoNomeArquivo = fotoStorage.gerarNomeArquivo(foto.getNomeArquivo());
		
		foto.setNomeArquivo(novoNomeArquivo);
		foto = produtoRepository.salvarFoto(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
								.nomeArquivo(novoNomeArquivo)
								.inputStream(inputStream)
								.contentType(foto.getContentType())
								.size(foto.getTamanho())
								.build();
		
		fotoStorage.substituir(novaFoto, nomeArquivoExistente);
		
		return foto;
	}
	
	@Transactional
	public void excluirFoto(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = this.buscarOuFalhar(restauranteId, produtoId);
		
		produtoRepository.excluirFoto(fotoProduto);
		produtoRepository.flush();
		
		fotoStorage.remover(fotoProduto.getNomeArquivo());
	}
	
	public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.buscarFotoProduto(restauranteId, produtoId)
				.orElseThrow(() -> 
					new FotoProdutoNaoEncontradaException(String.format("Não existe foto referente a um produto com id %d e restaurante de id %d", produtoId, restauranteId)));
	}
}