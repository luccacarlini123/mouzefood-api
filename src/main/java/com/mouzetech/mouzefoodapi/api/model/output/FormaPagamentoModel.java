package com.mouzetech.mouzefoodapi.api.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamento")
@Getter@Setter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {

	@ApiModelProperty(value = "ID de uma forma de pagamento", example = "1")
	private Long id;
	
	@ApiModelProperty(value = "Descrição da forma de pagamento", example = "Cartão de crédito")
	private String descricao;
}