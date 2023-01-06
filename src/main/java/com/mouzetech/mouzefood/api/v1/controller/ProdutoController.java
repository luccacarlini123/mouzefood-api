package com.mouzetech.mouzefood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.assembler.ProdutoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.ProdutoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.ProdutoInput;
import com.mouzetech.mouzefood.api.v1.model.ProdutoModel;
import com.mouzetech.mouzefood.domain.model.Produto;
import com.mouzetech.mouzefood.domain.repository.ProdutoRepository;
import com.mouzetech.mouzefood.domain.service.CadastroProdutoService;
import com.mouzetech.mouzefood.openapi.controller.ProdutoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class ProdutoController implements ProdutoControllerOpenApi {

	private ProdutoRepository produtoRepository;
	private CadastroProdutoService cadastroProdutoService;
	private ProdutoModelAssembler produtoModelAssembler;
	private ProdutoInputDisassembler produtoInputDisassembler;
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<ProdutoModel> buscarProdutosDoRestaurante(@PathVariable Long restauranteId, @RequestParam(required = false, defaultValue = "false") Boolean buscarInativos){
		List<Produto> produtos = null;
		
		if(buscarInativos) {
			produtos = cadastroProdutoService.buscarProdutosDoRestaurante(restauranteId);
		} else {
			produtos = cadastroProdutoService.buscarProdutosAtivosDoRestaurante(restauranteId);
		}
		CollectionModel<ProdutoModel> collectionModelProduto = produtoModelAssembler.toCollectionModel(produtos);
		
		collectionModelProduto.add(apiLinkBuilder.linkToProdutos(restauranteId));
		
		return collectionModelProduto;
	}
	
	@GetMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProdutoModel> buscarProdutoDoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionarProdutoEmRestaurante(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoInputDisassembler.toObject(produtoInput);
		produto = cadastroProdutoService.adicionarProdutoEmRestaurante(restauranteId, produto);
		return produtoModelAssembler.toModel(produto);
	}
	
	@PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel atualizarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		produtoInputDisassembler.copyToObject(produtoInput, produto);
		return produtoModelAssembler.toModel(produtoRepository.save(produto));
	}
}