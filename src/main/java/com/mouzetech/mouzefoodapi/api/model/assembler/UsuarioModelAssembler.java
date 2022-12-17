package com.mouzetech.mouzefoodapi.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.controller.UsuarioController;
import com.mouzetech.mouzefoodapi.api.model.output.UsuarioModel;
import com.mouzetech.mouzefoodapi.domain.model.Usuario;

@Component
public class UsuarioModelAssembler
		extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;

	public UsuarioModelAssembler() {
		super(UsuarioController.class, UsuarioModel.class);
	}

	@Override
	public UsuarioModel toModel(Usuario usuario) {
		UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioModel);

		usuarioModel.add(apiLinkBuilder.linkToUsuario("usuarios"));

		usuarioModel.add(apiLinkBuilder.linkToGrupoUsuario(usuario.getId()));

		return usuarioModel;
	}

	@Override
	public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(apiLinkBuilder.linkToUsuario(IanaLinkRelations.SELF.toString()));
	}

}