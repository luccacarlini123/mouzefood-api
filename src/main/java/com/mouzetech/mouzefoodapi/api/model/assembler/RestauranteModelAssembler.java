package com.mouzetech.mouzefoodapi.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.controller.RestauranteController;
import com.mouzetech.mouzefoodapi.api.model.output.RestauranteModel;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(apiLinkBuilder.linkToRestaurante("restaurantes"));
		
		restauranteModel.add(apiLinkBuilder.linkToProdutos(restaurante.getId(), "produtos"));
	    
	    restauranteModel.getCozinha().add(
	    		apiLinkBuilder.linkToCozinha(restaurante.getCozinha().getId(),IanaLinkRelations.SELF.toString()));
	    
	    if (restauranteModel.getEndereco() != null 
	            && restauranteModel.getEndereco().getCidade() != null) {
	        restauranteModel.getEndereco().getCidade().add(
	        		apiLinkBuilder.linkToCidade(restaurante.getEndereco().getCidade().getId()));
	    }
	    
		restauranteModel.add(apiLinkBuilder.linkToFormasPagamentoRestaurante(restauranteModel.getId(), "formasPagamento"));
		restauranteModel.add(apiLinkBuilder.linkToUsuariosResponsaveisDoRestaurante(restauranteModel.getId(), "responsaveis"));
		
		if(restaurante.aberto()) {
			restauranteModel.add(apiLinkBuilder.linkToFecharRestaurante(restauranteModel.getId(), "fechar"));
		}
		
		if(restaurante.fechado()) {
			restauranteModel.add(apiLinkBuilder.linkToAbrirRestaurante(restauranteModel.getId(), "abrir"));
		}
		
		if(restaurante.ativo()) {
			restauranteModel.add(apiLinkBuilder.linkToDesativarRestaurante(restauranteModel.getId(), "desativar"));
		}
		
		if(restaurante.inativo()) {
			restauranteModel.add(apiLinkBuilder.linkToAtivarRestaurante(restauranteModel.getId(), "ativar"));
		}
		
		return restauranteModel;
	}
	
	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		CollectionModel<RestauranteModel> collectionModelRestaurante = super.toCollectionModel(restaurantes);
		
		collectionModelRestaurante.add(apiLinkBuilder.linkToRestaurante(IanaLinkRelations.SELF.toString()));
		
		return collectionModelRestaurante;
	}
}