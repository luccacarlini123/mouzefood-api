package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.mouzetech.mouzefoodapi.api.model.assembler.RestauranteModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.RestauranteInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.RestauranteInput;
import com.mouzetech.mouzefoodapi.api.model.output.RestauranteModel;
import com.mouzetech.mouzefoodapi.api.model.view.RestauranteModelView;
import com.mouzetech.mouzefoodapi.domain.exception.NegocioException;
import com.mouzetech.mouzefoodapi.domain.exception.RestauranteNaoEncontradoException;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	private CozinhaRepository cozinhaRepository;
	private RestauranteRepository restauranteRepository;
	private CadastroRestauranteService cadastroRestauranteService;
//	private SmartValidator smartValidator;
	private RestauranteModelAssembler restauranteModelAssembler;
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@JsonView(RestauranteModelView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> listar(){
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@JsonView(RestauranteModelView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarApenasNome(){
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/buscar-primeiro")
	public ResponseEntity<RestauranteModel> findFirst() {
		Restaurante restaurante = restauranteRepository.buscarPrimeiro().get();
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}
	
	@GetMapping("/restaurantes/{cozinhaId}")
	public ResponseEntity<List<RestauranteModel>> buscarRestaurantes(@PathVariable Long cozinhaId){
		return ResponseEntity.ok(restauranteModelAssembler.toCollectionModel(cozinhaRepository.buscarRestaurantes(cozinhaId)));
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<RestauranteModel> buscarPorId(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}
	
	@PostMapping
	public ResponseEntity<RestauranteModel> salvar(@RequestBody @Valid RestauranteInput restauranteInput){
		Restaurante restaurante = cadastroRestauranteService.salvar(restauranteInputDisassembler.toObjectEntity(restauranteInput));
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteModelAssembler.toModel(restaurante));
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput){
		Restaurante restaurante = cadastroRestauranteService.atualizar(restauranteInput, restauranteId);
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}
	
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.desativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarRestaurantes(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.ativarRestaurantes(restaurantesId);			
		} catch(RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarRestaurantes(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.desativarRestaurantes(restaurantesId);			
		} catch(RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
	}
	
//	@PatchMapping("/{restauranteId}")
//	public ResponseEntity<?> atualizarParcialmente(@RequestBody Map<String, Object> campos, @PathVariable Long restauranteId, @RequestParam("cozinhaId") Long cozinhaId, HttpServletRequest httpServletRequest){			
//			Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
//			if(restaurante.isEmpty()) {
//				return ResponseEntity.notFound().build();
//			}
//			merge(campos, restaurante.get(), httpServletRequest);
//			validate(restaurante.get(), "restaurante");
//			return atualizar(restauranteId, restaurante.get(), cozinhaId);
//	}
//
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//		
//		smartValidator.validate(restaurante, bindingResult);
//		
//		if(bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//	}
//
//	private void merge(Map<String, Object> campos, Restaurante restaurante, HttpServletRequest httpServletRequest) {
//		ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurante restauranteOrigem = mapper.convertValue(campos, Restaurante.class);
//			
//			campos.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//				
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//				
//				ReflectionUtils.setField(field, restaurante, novoValor);
//			});
//		} catch(IllegalArgumentException ex) {
//			Throwable rootCause = ExceptionUtils.getRootCause(ex);
//			throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, servletServerHttpRequest);
//		}
//	}
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao){
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(restaurantesModel);
//
//		mappingJacksonValue.setSerializationView(RestauranteModelView.Resumo.class);
//		
//		if("apenas-nome".equals(projecao)) {
//			mappingJacksonValue.setSerializationView(RestauranteModelView.ApenasNome.class);
//		} else if("completo".equals(projecao)) {
//			mappingJacksonValue.setSerializationView(null);
//		}
//		
//		return mappingJacksonValue;
//	}
}