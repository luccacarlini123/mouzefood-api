package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.mouzetech.mouzefoodapi.api.data.PageableTranslator;
import com.mouzetech.mouzefoodapi.api.model.assembler.PedidoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.PedidoInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.PedidoInput;
import com.mouzetech.mouzefoodapi.api.model.output.PedidoModel;
import com.mouzetech.mouzefoodapi.api.model.output.PedidoResumoModel;
import com.mouzetech.mouzefoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.mouzetech.mouzefoodapi.domain.exception.NegocioException;
import com.mouzetech.mouzefoodapi.domain.filter.PedidoFilter;
import com.mouzetech.mouzefoodapi.domain.infrastructure.specification.PedidoSpecs;
import com.mouzetech.mouzefoodapi.domain.model.Pedido;
import com.mouzetech.mouzefoodapi.domain.model.Usuario;
import com.mouzetech.mouzefoodapi.domain.repository.PedidoRepository;
import com.mouzetech.mouzefoodapi.domain.service.EmissaoPedidoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

	private PedidoModelAssembler pedidoModelAssembler;
	private PedidoInputDisassembler pedidoInputDisassembler;
	private PedidoRepository pedidoRepository;
	private EmissaoPedidoService emissaoPedidoService;
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//		List<PedidoResumoModel> pedidosModel = pedidoModelAssembler.toCollectionResumoModel(pedidoRepository.findAll());
//		
//		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
//		simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));			
//		}
//		
//		mappingJacksonValue.setFilters(simpleFilterProvider);
//		
//		return mappingJacksonValue;
//	}
	
	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable){
		pageable = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		
		List<PedidoResumoModel> pedidosResumoModel = pedidoModelAssembler.toCollectionResumoModel(pedidosPage.getContent());
		
		Page<PedidoResumoModel> pagePedidoResumoModel = new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
		
		return pagePedidoResumoModel;
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"nomeCliente", "cliente.nome",
				"restaurante.nome", "restaurante.nome",
				"valorTotal", "valorTotal");

		return PageableTranslator.translate(pageable, mapeamento);
	}

	@GetMapping("/{pedidoCodigo}")
	public PedidoModel buscarPorId(@PathVariable String pedidoCodigo){
		return pedidoModelAssembler.toModel(emissaoPedidoService.buscarPorCodigo(pedidoCodigo));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel emitirPedido(@Valid @RequestBody PedidoInput pedidoInput) {
		try {			
			Pedido novoPedido = pedidoInputDisassembler.toObject(pedidoInput);
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
			novoPedido = emissaoPedidoService.emitir(novoPedido);
			
			return pedidoModelAssembler.toModel(novoPedido);
		} catch(EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
}