package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.ProdutoController;
import com.mouzetech.mouzefood.api.v1.model.ProdutoModel;
import com.mouzetech.mouzefood.domain.model.Produto;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

	public ProdutoModelAssembler() {
		super(ProdutoController.class, ProdutoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	@Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(apiLinkBuilder.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        produtoModel.add(apiLinkBuilder.linkToFotoProduto(produto.getRestaurante().getId(), produtoModel.getId(), "foto"));
        
        return produtoModel;
    } 
	
	@Override
	public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends Produto> entities) {
		CollectionModel<ProdutoModel> collectionModelProduto = super.toCollectionModel(entities);
		
		return collectionModelProduto;
	}
}
