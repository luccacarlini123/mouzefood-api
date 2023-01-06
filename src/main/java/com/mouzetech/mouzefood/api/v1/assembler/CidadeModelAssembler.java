package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.CidadeController;
import com.mouzetech.mouzefood.api.v1.model.CidadeModel;
import com.mouzetech.mouzefood.domain.model.Cidade;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(apiLinkBuilder.linkToCidade("cidades"));
		
		cidadeModel.getEstado().add(apiLinkBuilder.linkToEstado(cidadeModel.getEstado().getId()));
		return cidadeModel;
	}
	
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeModel> cidadeModelCollection = super.toCollectionModel(entities);
		
		cidadeModelCollection.add(apiLinkBuilder.linkToCidade(IanaLinkRelations.SELF.toString()));
		
		return cidadeModelCollection;
	}
}