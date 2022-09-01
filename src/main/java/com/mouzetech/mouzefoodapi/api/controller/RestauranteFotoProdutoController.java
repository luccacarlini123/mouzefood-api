package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.model.assembler.FotoProdutoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.FotoProdutoModel;
import com.mouzetech.mouzefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;
import com.mouzetech.mouzefoodapi.domain.service.CatalogoFotoProdutoService;
import com.mouzetech.mouzefoodapi.domain.service.FotoStorageService;
import com.mouzetech.mouzefoodapi.domain.service.FotoStorageService.FotoRecuperada;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@AllArgsConstructor
public class RestauranteFotoProdutoController {

	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	private FotoStorageService fotoStorage;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return fotoProdutoModelAssembler.toModel(catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId));
	}
	
	@GetMapping
	public ResponseEntity<?> buscarFotoDoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaTypes(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
			
			if(fotoRecuperada.temUrl()) {
				return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, fotoRecuperada.getUrl()).build();
			} else {
				return ResponseEntity.ok()
						.contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch(EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	private void verificarCompatibilidadeMediaTypes(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirFotoDoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoFotoProdutoService.excluirFoto(restauranteId, produtoId);
	}
}