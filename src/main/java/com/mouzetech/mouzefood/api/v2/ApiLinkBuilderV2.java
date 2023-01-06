package com.mouzetech.mouzefood.api.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.controller.CidadeController;

@Component
public class ApiLinkBuilderV2 {

	public Link linkToCidade(Long cidadeId) {
		return linkTo(methodOn(CidadeController.class).buscarPorId(cidadeId)).withSelfRel();
	}

	public Link linkToCidade(String relation) {
		return linkTo(CidadeController.class).withRel(relation);
	}

}