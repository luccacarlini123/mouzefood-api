package com.mouzetech.mouzefoodapi.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.output.EstadoModel;
import com.mouzetech.mouzefoodapi.domain.model.Estado;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EstadoModelAssembler {

	private ModelMapper modelMapper;
	
	public EstadoModel toModel(Estado estado) {
		return modelMapper.map(estado, EstadoModel.class);
	}
	
	public List<EstadoModel> toCollectionModel(List<Estado> estados){
		return estados.stream()
				.map(estado -> toModel(estado))
				.collect(Collectors.toList());
	}
}