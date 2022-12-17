package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.UsuarioModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("UsuariosModel")
@Getter
@Setter
public class UsuarioModelOpenApi {	
	
	private UsuariosEmbbededOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("UsuariosEmbbededModel")
	public class UsuariosEmbbededOpenApi {
		private List<UsuarioModel> usuarios;
	}	
}