package com.mouzetech.mouzefoodapi.api.model.output;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.mouzetech.mouzefoodapi.api.model.view.RestauranteModelView;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RestauranteModel {
	
	@JsonView( {RestauranteModelView.Resumo.class, RestauranteModelView.ApenasNome.class} )
	private Long id;
	
	@JsonView( {RestauranteModelView.Resumo.class, RestauranteModelView.ApenasNome.class} )
	private String nome;
	
	@JsonView(RestauranteModelView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteModelView.Resumo.class)
	private CozinhaModel cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoModel endereco;
}