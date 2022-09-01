package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.mouzetech.mouzefoodapi.api.model.assembler.ProdutoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.ProdutoInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.ProdutoInput;
import com.mouzetech.mouzefoodapi.api.model.output.ProdutoModel;
import com.mouzetech.mouzefoodapi.domain.model.Produto;
import com.mouzetech.mouzefoodapi.domain.repository.ProdutoRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroProdutoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
@AllArgsConstructor
public class ProdutoController {

	private ProdutoRepository produtoRepository;
	private CadastroProdutoService cadastroProdutoService;
	private ProdutoModelAssembler produtoModelAssembler;
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProdutoModel> buscarProdutosDoRestaurante(@PathVariable Long restauranteId, @RequestParam(required = false) boolean buscarInativos){
		List<Produto> produtos = null;
		
		if(buscarInativos) {
			produtos = cadastroProdutoService.buscarProdutosDoRestaurante(restauranteId);
		} else {
			produtos = cadastroProdutoService.buscarProdutosAtivosDoRestaurante(restauranteId);
		}
		
		return produtoModelAssembler.toCollectionModel(produtos);
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoModel> buscarProdutoDoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId){
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		return ResponseEntity.ok(produtoModelAssembler.toModel(produto));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionarProdutoEmRestaurante(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = produtoInputDisassembler.toObject(produtoInput);
		produto = cadastroProdutoService.adicionarProdutoEmRestaurante(restauranteId, produto);
		return produtoModelAssembler.toModel(produto);
	}
	
	@PutMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoModel atualizarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		produtoInputDisassembler.copyToObject(produtoInput, produto);
		return produtoModelAssembler.toModel(produtoRepository.save(produto));
	}
}