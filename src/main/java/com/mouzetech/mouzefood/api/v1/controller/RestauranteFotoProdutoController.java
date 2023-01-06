package com.mouzetech.mouzefood.api.v1.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mouzetech.mouzefood.api.v1.assembler.FotoProdutoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.FotoProdutoInput;
import com.mouzetech.mouzefood.api.v1.model.FotoProdutoModel;
import com.mouzetech.mouzefood.domain.exception.EntidadeNaoEncontradaException;
import com.mouzetech.mouzefood.domain.model.FotoProduto;
import com.mouzetech.mouzefood.domain.model.Produto;
import com.mouzetech.mouzefood.domain.service.CadastroProdutoService;
import com.mouzetech.mouzefood.domain.service.CatalogoFotoProdutoService;
import com.mouzetech.mouzefood.domain.service.FotoStorageService;
import com.mouzetech.mouzefood.domain.service.FotoStorageService.FotoRecuperada;
import com.mouzetech.mouzefood.openapi.controller.RestauranteFotoProdutoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@AllArgsConstructor
public class RestauranteFotoProdutoController implements RestauranteFotoProdutoControllerOpenApi {

	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	private FotoStorageService fotoStorage;
	private CadastroProdutoService cadastroProdutoService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProduto,
			@RequestPart(required = true) MultipartFile arquivo)  throws IOException {
		
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		
//		MultipartFile arquivo = fotoProduto.getArquivo();
		
		FotoProduto fotoSalva = new FotoProduto();
		fotoSalva.setProduto(produto);
		fotoSalva.setDescricao(fotoProduto.getDescricao());
		fotoSalva.setNomeArquivo(arquivo.getOriginalFilename());
		fotoSalva.setContentType(arquivo.getContentType());
		fotoSalva.setTamanho(arquivo.getSize());
		
		return fotoProdutoModelAssembler.toModel(catalogoFotoProdutoService.salvarFoto(fotoSalva, arquivo.getInputStream()));
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return fotoProdutoModelAssembler.toModel(catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId));
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE)
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