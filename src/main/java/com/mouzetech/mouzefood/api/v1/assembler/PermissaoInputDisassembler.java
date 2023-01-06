package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.PermissaoInput;
import com.mouzetech.mouzefood.domain.model.Permissao;

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