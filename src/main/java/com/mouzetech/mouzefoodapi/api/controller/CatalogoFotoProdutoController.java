package com.mouzetech.mouzefoodapi.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mouzetech.mouzefoodapi.api.model.assembler.FotoProdutoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.input.FotoProdutoInput;
import com.mouzetech.mouzefoodapi.api.model.output.FotoProdutoModel;
import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;
import com.mouzetech.mouzefoodapi.domain.model.Produto;
import com.mouzetech.mouzefoodapi.domain.service.CadastroProdutoService;
import com.mouzetech.mouzefoodapi.domain.service.CatalogoFotoProdutoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
@AllArgsConstructor
public class CatalogoFotoProdutoController {
	
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	private CadastroProdutoService cadastroProdutoService;
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProduto) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarProdutoDoRestaurante(restauranteId, produtoId);
		
		MultipartFile multipartFile = fotoProduto.getArquivo();
		
		FotoProduto fotoSalva = new FotoProduto();
		fotoSalva.setProduto(produto);
		fotoSalva.setDescricao(fotoProduto.getDescricao());
		fotoSalva.setNomeArquivo(multipartFile.getOriginalFilename());
		fotoSalva.setContentType(multipartFile.getContentType());
		fotoSalva.setTamanho(multipartFile.getSize());
		
		return fotoProdutoModelAssembler.toModel(catalogoFotoProdutoService.salvarFoto(fotoSalva, multipartFile.getInputStream()));
		
	}
}