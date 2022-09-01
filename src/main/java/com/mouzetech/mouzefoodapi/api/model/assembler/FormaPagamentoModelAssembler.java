package com.mouzetech.mouzefoodapi.api.model.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.output.FormaPagamentoModel;
import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FormaPagamentoModelAssembler {

	private ModelMapper modelMapper;
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	
	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamento){
		return formasPagamento.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}
	
}
