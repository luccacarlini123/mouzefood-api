package com.mouzetech.mouzefoodapi.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.input.PermissaoInput;
import com.mouzetech.mouzefoodapi.domain.model.Permissao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PermissaoInputDisassembler {

	private ModelMapper modelMapper;
	
	public Permissao toObject(PermissaoInput permissaoInput) {
		return modelMapper.map(permissaoInput, Permissao.class);
	}
	
	public void copyToObject(PermissaoInput permissaoInput, Permissao permissao) {
		modelMapper.map(permissaoInput, permissao);
	}
}