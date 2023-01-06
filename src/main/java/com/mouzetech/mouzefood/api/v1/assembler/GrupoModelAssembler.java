package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.GrupoController;
import com.mouzetech.mouzefood.api.v1.model.GrupoModel;
import com.mouzetech.mouzefood.domain.model.Grupo;

@Component
public class GrupoModelAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

	public GrupoModelAssembler() {
		super(GrupoController.class, GrupoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public GrupoModel toModel(Grupo grupo) {
		GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoModel);
		
		grupoModel.add(apiLinkBuilder.linkToGrupo("grupos"));
		grupoModel.add(apiLinkBuilder.linkToPermissoesGrupo(grupoModel.getId(), "permissoes"));
		
		return grupoModel;
	}
	
	@Override
	public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoModel> collectionModelGrupo = super.toCollectionModel(entities);
		
		collectionModelGrupo.add(apiLinkBuilder.linkToGrupo(IanaLinkRelations.SELF.toString()));
		
		return collectionModelGrupo;
	}
}