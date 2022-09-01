package com.mouzetech.mouzefoodapi.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.output.PermissaoModel;
import com.mouzetech.mouzefoodapi.domain.model.Permissao;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PermissaoModelAssembler {

	private ModelMapper modelMapper;
	
	public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	
	public List<PermissaoModel> toCollectionModel(List<Permissao> permissoes){
		return permissoes.stream().map(permissao -> toModel(permissao)).collect(Collectors.toList());
	}
	
}
