package com.mouzetech.mouzefoodapi.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CidadeModel {
	private Long id;
	private String nome;
	private EstadoModel estado;
}