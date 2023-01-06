package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.RestauranteController;
import com.mouzetech.mouzefood.api.v1.model.RestauranteResumoModel;
import com.mouzetech.mouzefood.domain.model.Restaurante;

@Component
public class RestauranteResumoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteResumoModel> {

	public RestauranteResumoModelAssembler() {
		super(RestauranteController.class, RestauranteResumoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;

	@Override
	public RestauranteResumoModel toModel(Restaurante restaurante) {
		RestauranteResumoModel restauranteResumoModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteResumoModel);
						
		return restauranteResumoModel;
	}
	
	@Override
	public CollectionModel<RestauranteResumoModel> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		CollectionModel<RestauranteResumoModel> collectionModelRestauranteResumo = super.toCollectionModel(restaurantes);
		collectionModelRestauranteResumo.add(apiLinkBuilder.linkToRestaurante(IanaLinkRelations.SELF.toString()));
		
		return collectionModelRestauranteResumo;
	}
}