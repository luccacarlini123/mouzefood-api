package com.mouzetech.mouzefood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.CidadeController;
import com.mouzetech.mouzefood.api.v2.model.CidadeModelV2;
import com.mouzetech.mouzefood.domain.model.Cidade;

@Component
public class CidadeModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeModelV2> {

	public CidadeModelAssemblerV2() {
		super(CidadeController.class, CidadeModelV2.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public CidadeModelV2 toModel(Cidade cidade) {
		CidadeModelV2 cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(apiLinkBuilder.linkToCidade("cidades"));
		
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModelV2> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeModelV2> cidadeModelCollection = super.toCollectionModel(entities);
		
		cidadeModelCollection.add(apiLinkBuilder.linkToCidade(IanaLinkRelations.SELF.toString()));
		
		return cidadeModelCollection;
	}
}