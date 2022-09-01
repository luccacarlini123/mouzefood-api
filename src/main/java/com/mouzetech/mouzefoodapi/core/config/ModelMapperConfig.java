package com.mouzetech.mouzefoodapi.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.input.ItemPedidoInput;
import com.mouzetech.mouzefoodapi.api.model.output.EnderecoModel;
import com.mouzetech.mouzefoodapi.domain.model.Endereco;
import com.mouzetech.mouzefoodapi.domain.model.ItemPedido;

@Component
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(
				enderecoSource -> enderecoSource.getCidade().getEstado().getNome(), 
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		return modelMapper;
	}
}