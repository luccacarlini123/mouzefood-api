package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.PermissaoController;
import com.mouzetech.mouzefood.api.v1.model.PermissaoModel;
import com.mouzetech.mouzefood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

	public PermissaoModelAssembler() {
		super(PermissaoController.class, PermissaoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public PermissaoModel toModel(Permissao permissao) {
		PermissaoModel permissaoModel = createModelWithId(permissao.getId(), permissao);
		modelMapper.map(permissao, permissaoModel);
		
		permissaoModel.add(apiLinkBuilder.linkToPermissao("permissoes"));
		
		return permissaoModel;
	}
	
	@Override
	public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
		CollectionModel<PermissaoModel> collectionModelPermissao = super.toCollectionModel(entities);
		
		collectionModelPermissao.add(apiLinkBuilder.linkToPermissao(IanaLinkRelations.SELF.toString()));
		
		return collectionModelPermissao;
	}
	
}
