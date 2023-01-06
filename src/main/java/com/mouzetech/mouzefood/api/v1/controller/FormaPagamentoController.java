package com.mouzetech.mouzefood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.mouzetech.mouzefood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoInput;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;
import com.mouzetech.mouzefood.domain.model.FormaPagamento;
import com.mouzetech.mouzefood.domain.repository.FormaPagamentoRepository;
import com.mouzetech.mouzefood.domain.service.CadastroFormaPagamentoService;
import com.mouzetech.mouzefood.openapi.controller.FormaPagamentoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/formas-pagamento")
@AllArgsConstructor
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	private FormaPagamentoRepository formaPagamentoRepository;
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> buscar(ServletWebRequest request){
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime maiorDataAtualizacao = formaPagamentoRepository.obterMaiorDataAtualizacao();
		
		if(maiorDataAtualizacao != null) {
			eTag = String.valueOf(maiorDataAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = 
				formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());
		
		return ResponseEntity
				.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formasPagamentoModel);
	}
	
	@GetMapping(value = "/{formaPagamentoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long formaPagamentoId, ServletWebRequest request){
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime maiorDataAtualizacao = formaPagamentoRepository.obterMaiorDataAtualizacaoPorId(formaPagamentoId);
		
		if(maiorDataAtualizacao != null) {
			eTag = String.valueOf(maiorDataAtualizacao.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamentoModel formaPagamentoModel = 
				formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.buscarPorId(formaPagamentoId));
		
		return ResponseEntity
				.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toObjectDomain(formaPagamentoInput);
		return ResponseEntity.status(HttpStatus.CREATED).body((formaPagamentoModelAssembler.toModel(formaPagamentoRepository.save(formaPagamento))));
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarPorId(formaPagamentoId);
		
		formaPagamentoInputDisassembler.copyToObjectDomain(formaPagamentoInput, formaPagamentoAtual);
		
		formaPagamentoRepository.save(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
}
