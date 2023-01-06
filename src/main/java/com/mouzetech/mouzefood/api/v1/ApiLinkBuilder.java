package com.mouzetech.mouzefood.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.controller.CidadeController;
import com.mouzetech.mouzefood.api.v1.controller.CozinhaController;
import com.mouzetech.mouzefood.api.v1.controller.EstadoController;
import com.mouzetech.mouzefood.api.v1.controller.EstatisticasController;
import com.mouzetech.mouzefood.api.v1.controller.FluxoPedidoController;
import com.mouzetech.mouzefood.api.v1.controller.FormaPagamentoController;
import com.mouzetech.mouzefood.api.v1.controller.GrupoController;
import com.mouzetech.mouzefood.api.v1.controller.GrupoPermissaoController;
import com.mouzetech.mouzefood.api.v1.controller.PedidoController;
import com.mouzetech.mouzefood.api.v1.controller.PermissaoController;
import com.mouzetech.mouzefood.api.v1.controller.ProdutoController;
import com.mouzetech.mouzefood.api.v1.controller.RestauranteController;
import com.mouzetech.mouzefood.api.v1.controller.RestauranteFormasPagamentoController;
import com.mouzetech.mouzefood.api.v1.controller.RestauranteFotoProdutoController;
import com.mouzetech.mouzefood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.mouzetech.mouzefood.api.v1.controller.UsuarioController;
import com.mouzetech.mouzefood.api.v1.controller.UsuarioGrupoController;

@Component
public class ApiLinkBuilder {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
    		new TemplateVariable("page", VariableType.REQUEST_PARAM),
    		new TemplateVariable("size", VariableType.REQUEST_PARAM),
    		new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos(String relation) {
	    TemplateVariables templateVariablesPedidoFilter = new TemplateVariables(
	    		new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
	    
	    String pedidoUrl = linkTo(PedidoController.class).toUri().toString();
	    
	    return new Link(UriTemplate.of(pedidoUrl, PAGINACAO_VARIABLES.concat(templateVariablesPedidoFilter)), relation);
	}
		
	public Link linkToCancelarPedido(String pedidoCodigo, String relation) {
		return linkTo(methodOn(FluxoPedidoController.class).cancelarPedido(pedidoCodigo))
				.withRel(relation);
	}
	
	public Link linkToEntregarPedido(String pedidoCodigo, String relation) {
		return linkTo(methodOn(FluxoPedidoController.class).entregarPedido(pedidoCodigo))
				.withRel(relation);
	}
	
	public Link linkToConfirmarPedido(String pedidoCodigo, String relation) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmarPedido(pedidoCodigo))
				.withRel(relation);
	}
	
	public Link linkToUsuario(Long usuarioId) {
		return linkTo(methodOn(UsuarioController.class).buscarPorId(usuarioId))
				.withSelfRel();
	}
	
	public Link linkToUsuario(String relation) {
		return linkTo(UsuarioController.class).withRel(relation);
	}
	
	public Link linkToGrupoUsuario(Long usuarioId) {
		return linkTo(methodOn(UsuarioGrupoController.class)
				.buscarGruposDeUmUsuario(usuarioId))
		.withRel("grupos-usuario");
	}
	
	public Link linkToUsuariosRestaurante(Long restauranteId) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.buscarUsuariosResponsaveisDoRestaurante(restauranteId))
				.withSelfRel();
	}
	
	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.buscarPorId(formaPagamentoId, null))
				.withSelfRel();
	}
	
	public Link linkToCidade(Long cidadeId) {
		return linkTo(methodOn(CidadeController.class)
				.buscarPorId(cidadeId))
				.withSelfRel();
	}
	
	public Link linkToCidade(String relation) {
		return linkTo(CidadeController.class).withRel(relation);
	}
	
	public Link linkToEstado(Long estadoId) {
		return linkTo(methodOn(EstadoController.class)
				.buscarPorId(estadoId))
				.withSelfRel();
	}
	
	public Link linkToEstado(String relation) {
		return linkTo(EstadoController.class)
				.withRel(relation);
	}
	
	public Link linkToCozinha(String relation) {
		return linkTo(CozinhaController.class)
				.withRel(relation);
	}
	
	public Link linkToCozinha(Long cozinhaId, String rel) {
		return linkTo(methodOn(CozinhaController.class).buscarPorId(cozinhaId))
				.withRel(rel);
	}
	
	public Link linkToRestaurante(String relation) {
		TemplateVariables templateVariablesApenasNome = new TemplateVariables(
	    		new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
		
		String restauranteUrl = linkTo(RestauranteController.class).toUri().toString();
		
		return new Link(UriTemplate
				.of(restauranteUrl, templateVariablesApenasNome), relation);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return linkTo(methodOn(RestauranteController.class)
				.buscarPorId(restauranteId))
				.withSelfRel();
	}
	
	public Link linkToRestaurante() {
		return linkTo(RestauranteController.class)
				.withSelfRel();
	}
	
	public Link linkToAbrirRestaurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId))
				.withRel(relation);
	}
	
	public Link linkToFecharRestaurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId))
				.withRel(relation);
	}
	
	public Link linkToAtivarRestaurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteController.class).ativar(restauranteId))
				.withRel(relation);
	}
	
	public Link linkToDesativarRestaurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteController.class).desativar(restauranteId))
				.withRel(relation);
	}
	
	public Link linkToUsuariosResponsaveisDoRestaurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).buscarUsuariosResponsaveisDoRestaurante(restauranteId))
				.withRel(relation);
	}

	public Link linkToFormasPagamentoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormasPagamentoController.class)
				.buscarFormasPagamento(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToFormaPagamento(String rel) {
		return linkTo(FormaPagamentoController.class)
				.withRel(rel);
	}
	
	public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.buscarPorId(formaPagamentoId, null))
				.withRel(rel);
	}
	
	public Link linkToDesassociarFormaPagamentoResataurante(Long restauranteId, Long formaPagamentoId, String relation) {
		return linkTo(methodOn(RestauranteFormasPagamentoController.class).desassociarFormaPagamento(restauranteId, formaPagamentoId))
				.withRel(relation);
	}
	
	public Link linkToAssociarFormaPagamentoResataurante(Long restauranteId, String relation) {
		return linkTo(methodOn(RestauranteFormasPagamentoController.class).associarFormaPagamento(restauranteId, null))
				.withRel(relation);
	}
	
	public Link linkToAdicionarUsuarioResponsavelNoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).adicionarResponsavelAoRestaurante(restauranteId, null))
				.withRel(rel);
	}
	
	public Link linkToRemoverUsuarioResponsavelNoRestaurante(Long restauranteId, Long usuarioId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).removerResponsavelAoRestaurante(restauranteId, usuarioId))
				.withRel(rel);
	}
	
	public Link linkToProdutos(Long restauranteId, String rel) {
	    return linkTo(methodOn(ProdutoController.class)
	            .buscarProdutosDoRestaurante(restauranteId, null)).withRel(rel);
	}

	public Link linkToProdutos(Long restauranteId) {
	    return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToProdutos(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(ProdutoController.class)
				.buscarProdutoDoRestaurante(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
		return linkTo(methodOn(RestauranteFotoProdutoController.class).buscar(restauranteId, produtoId))
				.withRel(rel);
	}
	
	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
		return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.toString());
	}
	
	public Link linkToGrupo(String rel) {
		return linkTo(GrupoController.class)
				.withRel(rel);
	}
	
	public Link linkToGrupo(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoController.class).buscarPorId(grupoId))
				.withRel(rel);
	}
	
	public Link linkToPermissoesGrupo(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).buscarPermissoesDoGrupo(grupoId))
				.withRel(rel);
	}
	
	public Link linkToPermissao(String rel) {
		return linkTo(PermissaoController.class)
				.withRel(rel);
	}
	
	public Link linkToPermissao(Long permissaoId, String rel) {
		return linkTo(methodOn(PermissaoController.class).buscarPorId(permissaoId))
				.withRel(rel);
	}
	
	public Link linkToAssociarPermissaoGrupo(Long grupoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).associarPermissao(grupoId, null))
				.withRel(rel);
	}
	
	public Link linkToDesassociarPermissaoGrupo(Long grupoId, Long permissaoId, String rel) {
		return linkTo(methodOn(GrupoPermissaoController.class).desassociarPermissao(grupoId, permissaoId))
				.withRel(rel);
	}
	
	public Link linkToAssociarUsuarioGrupo(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).associarGrupo(usuarioId, null))
				.withRel(rel);
	}
	
	public Link linkToDesassociarUsuarioGrupo(Long grupoId, Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).desassociarGrupo(usuarioId, grupoId))
				.withRel(rel);
	}
	
	public Link linkToEstatisticas(String rel) {
		return linkTo(methodOn(EstatisticasController.class).buscarEndpointsDeEstatisticas())
				.withRel(rel);
	}
	
	public Link linkToVendasDiarias(String rel) {
		TemplateVariables templateVariablesVendaFilter = new TemplateVariables(
	    		new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
	    		new TemplateVariable("offset", VariableType.REQUEST_PARAM));
		
		String vendasDiariasUrl = linkTo(methodOn(EstatisticasController.class).buscarVendasDiarias(null, null)).toUri().toString();
		
		return new Link(UriTemplate.of(vendasDiariasUrl, templateVariablesVendaFilter), rel);
	}
}