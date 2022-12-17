package com.mouzetech.mouzefoodapi.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.controller.RestauranteFotoProdutoController;
import com.mouzetech.mouzefoodapi.api.model.output.FotoProdutoModel;
import com.mouzetech.mouzefoodapi.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

	public FotoProdutoModelAssembler() {
		super(RestauranteFotoProdutoController.class, FotoProdutoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public FotoProdutoModel toModel(FotoProduto foto) {
		FotoProdutoModel fotoProdutoModel = modelMapper.map(foto, FotoProdutoModel.class);
		
		fotoProdutoModel.add(apiLinkBuilder.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));
		
		fotoProdutoModel.add(apiLinkBuilder.linkToProdutos(
                foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
		
		return fotoProdutoModel;
	}
}