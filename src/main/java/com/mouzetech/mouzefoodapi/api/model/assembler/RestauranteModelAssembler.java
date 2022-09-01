package com.mouzetech.mouzefoodapi.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.output.RestauranteModel;
import com.mouzetech.mouzefoodapi.api.model.output.RestauranteResumoModel;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}
	
	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
	
	public RestauranteResumoModel toResumoModel(Restaurante restaurante) {
		return this.modelMapper.map(restaurante, RestauranteResumoModel.class);
	}
}