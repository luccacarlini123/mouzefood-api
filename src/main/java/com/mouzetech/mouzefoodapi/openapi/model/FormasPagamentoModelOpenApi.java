package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.FormaPagamentoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("FormasPagamentoModel")
@Getter
@Setter
public class FormasPagamentoModelOpenApi {

	private FormasPagamentoEmbbededModel _embbeded;
	private Links _links;
	
	@Data
	@ApiModel("FormasPagamentoEmbbededModel")
	public class FormasPagamentoEmbbededModel {
		private List<FormaPagamentoModel> formasPagamento;
	}
	
}
