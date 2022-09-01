package com.mouzetech.mouzefoodapi;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mouzetech.mouzefoodapi.domain.model.Cozinha;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCozinhaService;
import com.mouzetech.mouzefoodapi.domain.service.CadastroRestauranteService;
import com.mouzetech.mouzefoodapi.util.DatabaseCleaner;
import com.mouzetech.mouzefoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@LocalServerPort
	private int port;
	
	private Restaurante restauranteQuintalDiCasa;
	
	private Cozinha cozinhaMineira;
	
	private int quantidadeRestaurantes;
	
	private int ID_COZINHA_INEXISTENTE = 100000;
	
	String jsonCorretoRestauranteMareMansa = ResourceUtils.getContentFromResource("/json/correto/restaurante-mare_mansa.json");
	String jsonCorretoRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/correto/restaurante-com-cozinha-inexistente.json");
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/restaurantes";
		RestAssured.port = port;
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornar200_QuandoBuscarRestaurantesComSucesso() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar1Restaurante_QuandoBuscarRestaurantesComSucesso() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRestaurantes));
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarRestauranteComSucesso() {
		RestAssured.given()
			.body(jsonCorretoRestauranteMareMansa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		RestAssured.given()
			.body(jsonCorretoRestauranteComCozinhaInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	private void prepararDados() {
		Cozinha cozinhaMineira = new Cozinha();
		cozinhaMineira.setNome("Mineira");
		
		cozinhaMineira = cadastroCozinhaService.salvar(cozinhaMineira);
		this.cozinhaMineira = cozinhaMineira;
		
		Restaurante restauranteQuintalDiCasa = new Restaurante();
		restauranteQuintalDiCasa.setNome("Quintal Di Casa");
		restauranteQuintalDiCasa.setTaxaFrete(new BigDecimal(10));
		restauranteQuintalDiCasa.setCozinha(cozinhaMineira);
		
		restauranteQuintalDiCasa = cadastroRestauranteService.salvar(restauranteQuintalDiCasa);
		this.restauranteQuintalDiCasa = restauranteQuintalDiCasa;
		
		this.quantidadeRestaurantes = (int) restauranteRepository.count();
	}
}
