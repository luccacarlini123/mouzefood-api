package com.mouzetech.mouzefoodapi.api.model.output;

import com.fasterxml.jackson.annotation.JsonView;
import com.mouzetech.mouzefoodapi.api.model.view.RestauranteModelView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {
	
	@JsonView(RestauranteModelView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteModelView.Resumo.class)
	private String nome;
}