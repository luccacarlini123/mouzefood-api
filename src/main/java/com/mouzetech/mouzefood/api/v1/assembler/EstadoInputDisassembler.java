package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.EstadoInput;
import com.mouzetech.mouzefood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toObjectDomain(EstadoInput estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToObjectDomain(EstadoInput estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
}
