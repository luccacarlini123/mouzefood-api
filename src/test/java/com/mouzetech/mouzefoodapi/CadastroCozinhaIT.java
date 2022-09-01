package com.mouzetech.mouzefoodapi;

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
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCozinhaService;
import com.mouzetech.mouzefoodapi.util.DatabaseCleaner;
import com.mouzetech.mouzefoodapi.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

	private static final int ID_COZINHA_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Cozinha cozinhaBrasileira;
	
	private Cozinha cozinhaTailandesa;
	
	private int quantidadeCozinhasCadastradas;
	
	String jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");
	String jsonCozinhaComNomeVazio = ResourceUtils.getContentFromResource("/json/errado/cozinha-com-nome-vazio.json");
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		prepararDadosParaTeste();
	}
	
	@Test
	public void deveRetornar200_QuandoBuscarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConter2Cozinhas_QuandoBuscarCozinhas() {
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(this.quantidadeCozinhasCadastradas));
	}
	
	@Test
	public void deveRetornarCozinhasTailandesa_e_Brasileira_QuandoBuscarCozinhas() {
		RestAssured.given()
			.port(port)
		.when()
			.get()
		.then()
			.body("nome", Matchers.hasItems(cozinhaTailandesa.getNome(), cozinhaBrasileira.getNome()));
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarCozinha() {
		RestAssured.given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornar400_QuandoCadastrarCozinha_Com_Nome_Vazio() {
		RestAssured.given()
			.body(jsonCozinhaComNomeVazio)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornar200_e_Nome_Brasileira_QuandoBuscarCozinha_Com_Id_1() {
		RestAssured.given()
			.pathParam("cozinhaId", cozinhaBrasileira.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaBrasileira.getNome()));
	}
	
	@Test
	public void deveRetornar404_QuandoBuscarCozinha_Com_Id_Invalido() {
		RestAssured.given()
			.pathParam("cozinhaId", ID_COZINHA_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDadosParaTeste() {
		Cozinha cozinhaBrasileira = new Cozinha();
		cozinhaBrasileira.setNome("Brasileira");
		
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		
		this.cozinhaBrasileira = cadastroCozinhaService.salvar(cozinhaBrasileira);
		this.cozinhaTailandesa = cadastroCozinhaService.salvar(cozinhaTailandesa);
		
		this.quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
	
//Testes de integração abaixo que eu não quis excluir para ter exemplos
//	@Autowired
//	private CadastroCozinhaService cadastroCozinhaService;
//	
//	@Autowired
//	private CadastroRestauranteService cadastroRestauranteService;	
	
	
//	@Test
//	void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
//		Cozinha cozinha = new Cozinha();
//		cozinha.setNome("Chinesa");
//		
//		cozinha = cadastroCozinhaService.salvar(cozinha);
//		
//		assertThat(cozinha).isNotNull();
//		assertThat(cozinha.getId()).isNotNull();
//	}
//	
//	@Test
//	void deveFalhar_QuandoCadastrarCozinhaSemNome() {
//		Cozinha cozinha = new Cozinha();
//		cozinha.setNome(null);
//		
//		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
//			cadastroCozinhaService.salvar(cozinha);
//		});
//		
//		assertThat(erroEsperado).isNotNull();
//	}
//	
//	@Test
//	void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		EntidadeEmUsoException erroEsperado =
//		Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
//			Cozinha cozinha = new Cozinha();
//			cozinha.setNome("Chinesa");
//			
//			cozinha = cadastroCozinhaService.salvar(cozinha);
//			
//			Restaurante restaurante = new Restaurante();
//			restaurante.setNome("Quintal Di Casa");
//			restaurante.setCozinha(cozinha);
//			restaurante.setTaxaFrete(new BigDecimal(10));
//			
//			cadastroRestauranteService.salvar(restaurante, cozinha.getId());
//			cadastroCozinhaService.remover(cozinha.getId());
//		}); 
//		
//		assertThat(erroEsperado).isNotNull();
//	}
//	
//	@Test
//	void deveFalhar_QuandoExcluirCozinhaInexistente() {
//		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
//			cadastroCozinhaService.remover(102352105L);
//		});
//		
//		assertThat(erroEsperado).isNotNull();
//	}
}