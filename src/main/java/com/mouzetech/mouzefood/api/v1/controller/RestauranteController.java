package com.mouzetech.mouzefood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
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

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.mouzetech.mouzefood.api.v1.assembler.RestauranteInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.RestauranteModelAssembler;
import com.mouzetech.mouzefood.api.v1.assembler.RestauranteResumoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.RestauranteApenasNomeModel;
import com.mouzetech.mouzefood.api.v1.model.RestauranteInput;
import com.mouzetech.mouzefood.api.v1.model.RestauranteModel;
import com.mouzetech.mouzefood.api.v1.model.RestauranteResumoModel;
import com.mouzetech.mouzefood.domain.exception.NegocioException;
import com.mouzetech.mouzefood.domain.exception.RestauranteNaoEncontradoException;
import com.mouzetech.mouzefood.domain.model.Restaurante;
import com.mouzetech.mouzefood.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefood.domain.repository.RestauranteRepository;
import com.mouzetech.mouzefood.domain.service.CadastroRestauranteService;
import com.mouzetech.mouzefood.openapi.controller.RestauranteControllerOpenApi;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

	private CozinhaRepository cozinhaRepository;
	private RestauranteRepository restauranteRepository;
	private CadastroRestauranteService cadastroRestauranteService;
//	private SmartValidator smartValidator;
	private RestauranteModelAssembler restauranteModelAssembler;
	private RestauranteResumoModelAssembler restauranteResumoModelAssembler;
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;
	private RestauranteInputDisassembler restauranteInputDisassembler;
	private ApiLinkBuilder apiLinkBuilder;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<RestauranteResumoModel> listar() {
		CollectionModel<RestauranteResumoModel> collectionModelRestaurante =
				restauranteResumoModelAssembler.toCollectionModel(restauranteRepository.findAll());
		
		collectionModelRestaurante.getContent().forEach(restaurante -> {
			Long cozinhaId = restaurante.getCozinha().getId();
			restaurante.getCozinha().add(apiLinkBuilder.linkToCozinha(cozinhaId, IanaLinkRelations.SELF.toString()));
		});
		
		return collectionModelRestaurante;
	}

	@ApiOperation(value = "Lista todos os restaurantes", hidden = true)
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
		return restauranteApenasNomeModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}

	@GetMapping(value = "/buscar-primeiro", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestauranteModel> findFirst() {
		Restaurante restaurante = restauranteRepository.buscarPrimeiro().get();
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}

	@GetMapping(value = "/cozinhas/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<RestauranteModel> buscarRestaurantes(@PathVariable Long cozinhaId) {
		return restauranteModelAssembler.toCollectionModel(cozinhaRepository.buscarRestaurantes(cozinhaId));
	}

	@GetMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestauranteModel> buscarPorId(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestauranteModel> salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = cadastroRestauranteService
				.salvar(restauranteInputDisassembler.toObjectEntity(restauranteInput));
		return ResponseEntity.status(HttpStatus.CREATED).body(restauranteModelAssembler.toModel(restaurante));
	}

	@PutMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestauranteModel> atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = cadastroRestauranteService.atualizar(restauranteInput, restauranteId);
		return ResponseEntity.ok(restauranteModelAssembler.toModel(restaurante));
	}

	@PutMapping("/{restauranteId}/ativo")
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{restauranteId}/ativo")
	public ResponseEntity<Void> desativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.desativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarRestaurantes(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.ativarRestaurantes(restaurantesId);
		} catch (RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desativarRestaurantes(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.desativarRestaurantes(restaurantesId);
		} catch (RestauranteNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage());
		}
	}

	@PutMapping("/{restauranteId}/abertura")
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrir(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{restauranteId}/fechamento")
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fechar(restauranteId);
		return ResponseEntity.noContent().build();
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