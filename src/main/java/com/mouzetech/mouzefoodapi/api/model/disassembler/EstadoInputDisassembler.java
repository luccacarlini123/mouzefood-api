package com.mouzetech.mouzefoodapi.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.input.EstadoInput;
import com.mouzetech.mouzefoodapi.domain.model.Estado;

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
