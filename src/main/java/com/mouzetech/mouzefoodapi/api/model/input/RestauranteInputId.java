package com.mouzetech.mouzefoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RestauranteInputId {

	@NotNull
	private Long id;
	
}
