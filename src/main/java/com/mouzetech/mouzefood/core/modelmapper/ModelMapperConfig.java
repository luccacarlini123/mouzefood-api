package com.mouzetech.mouzefood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.EnderecoModel;
import com.mouzetech.mouzefood.api.v1.model.ItemPedidoInput;
import com.mouzetech.mouzefood.domain.model.Endereco;
import com.mouzetech.mouzefood.domain.model.ItemPedido;

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