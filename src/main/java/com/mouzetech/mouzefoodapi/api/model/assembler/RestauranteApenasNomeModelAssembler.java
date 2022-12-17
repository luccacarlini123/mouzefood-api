package com.mouzetech.mouzefoodapi.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.controller.RestauranteController;
import com.mouzetech.mouzefoodapi.api.model.output.RestauranteApenasNomeModel;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;

	@Override
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		RestauranteApenasNomeModel restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteApenasNomeModel);
		
		return restauranteApenasNomeModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		CollectionModel<RestauranteApenasNomeModel> collectionModelRestauranteApenasNome = super.toCollectionModel(restaurantes);
		collectionModelRestauranteApenasNome.add(apiLinkBuilder.linkToRestaurante(IanaLinkRelations.SELF.toString()));
		
		return collectionModelRestauranteApenasNome;
	}	
}