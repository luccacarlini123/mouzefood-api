package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.FormaPagamentoController;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;
import com.mouzetech.mouzefood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	@Override
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento); 
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		formaPagamentoModel.add(apiLinkBuilder.linkToFormaPagamento("formas-pagamento"));
		
		return formaPagamentoModel;
	}	
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoModel> formasPagamento = super.toCollectionModel(entities);
		formasPagamento.add(apiLinkBuilder.linkToFormaPagamento(IanaLinkRelations.SELF.toString()));
		
		return formasPagamento;
	}
}