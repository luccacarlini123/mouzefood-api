package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.RestauranteInput;
import com.mouzetech.mouzefood.domain.model.Cidade;
import com.mouzetech.mouzefood.domain.model.Cozinha;
import com.mouzetech.mouzefood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toObjectEntity(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		//para evitar
		//identifier of an instance of com.mouzetech.mouzefood.domain.model.Cozinha was altered from 3 to 1
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		
		modelMapper.map(restauranteInput, restaurante);
	}
	
}
