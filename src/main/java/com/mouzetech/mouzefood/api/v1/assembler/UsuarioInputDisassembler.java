package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.UsuarioInput;
import com.mouzetech.mouzefood.api.v1.model.UsuarioInputComSenha;
import com.mouzetech.mouzefood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toObject(UsuarioInputComSenha usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputComSenha usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
	public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
	
}
