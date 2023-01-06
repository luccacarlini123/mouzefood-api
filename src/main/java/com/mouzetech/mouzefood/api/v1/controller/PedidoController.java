package com.mouzetech.mouzefood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.mouzetech.mouzefood.api.data.PageWrapper;
import com.mouzetech.mouzefood.api.data.PageableTranslator;
import com.mouzetech.mouzefood.api.v1.assembler.PedidoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.PedidoModelAssembler;
import com.mouzetech.mouzefood.api.v1.assembler.PedidoResumoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.PedidoInput;
import com.mouzetech.mouzefood.api.v1.model.PedidoModel;
import com.mouzetech.mouzefood.api.v1.model.PedidoResumoModel;
import com.mouzetech.mouzefood.domain.exception.EntidadeNaoEncontradaException;
import com.mouzetech.mouzefood.domain.exception.NegocioException;
import com.mouzetech.mouzefood.domain.filter.PedidoFilter;
import com.mouzetech.mouzefood.domain.infrastructure.specification.PedidoSpecs;
import com.mouzetech.mouzefood.domain.model.Pedido;
import com.mouzetech.mouzefood.domain.model.Usuario;
import com.mouzetech.mouzefood.domain.repository.PedidoRepository;
import com.mouzetech.mouzefood.domain.service.EmissaoPedidoService;
import com.mouzetech.mouzefood.openapi.controller.PedidoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController implements PedidoControllerOpenApi {

	private PedidoModelAssembler pedidoModelAssembler;
	private PagedResourcesAssembler<Pedido> pagedResourcesAssemblerPedido;
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
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
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable){
		Pageable pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		PagedModel<PedidoResumoModel> pagedModelPedidoResumo = pagedResourcesAssemblerPedido.toModel(pedidosPage, pedidoResumoModelAssembler);
		
		return pagedModelPedidoResumo;
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"nomeCliente", "cliente.nome",
				"nomerestaurante", "restaurante.nome",
				"valorTotal", "valorTotal");

		return PageableTranslator.translate(pageable, mapeamento);
	}

	@GetMapping(value = "/{pedidoCodigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoModel buscarPorId(@PathVariable String pedidoCodigo){
		return pedidoModelAssembler.toModel(emissaoPedidoService.buscarPorCodigo(pedidoCodigo));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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