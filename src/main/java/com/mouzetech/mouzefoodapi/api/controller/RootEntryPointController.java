package com.mouzetech.mouzefoodapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;

@RestController
@RequestMapping
public class RootEntryPointController {

	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping
	public RootEntryPointModel rootEntryPoint() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(apiLinkBuilder.linkToCozinha("cozinhas"));
		rootEntryPointModel.add(apiLinkBuilder.linkToPedidos("pedidos"));
		rootEntryPointModel.add(apiLinkBuilder.linkToRestaurante("restaurantes"));
		rootEntryPointModel.add(apiLinkBuilder.linkToGrupo("grupos"));
		rootEntryPointModel.add(apiLinkBuilder.linkToUsuario("usuarios"));
		rootEntryPointModel.add(apiLinkBuilder.linkToPermissao("permissoes"));
		rootEntryPointModel.add(apiLinkBuilder.linkToFormaPagamento("formas-pagamento"));
		rootEntryPointModel.add(apiLinkBuilder.linkToEstado("estados"));
		rootEntryPointModel.add(apiLinkBuilder.linkToCidade("cidades"));
		rootEntryPointModel.add(apiLinkBuilder.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static final class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {}
}