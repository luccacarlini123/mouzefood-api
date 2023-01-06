package com.mouzetech.mouzefood.core.openapi;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mouzetech.mouzefood.api.exceptionhandler.Problem;
import com.mouzetech.mouzefood.api.v1.model.CidadeModel;
import com.mouzetech.mouzefood.api.v1.model.CozinhaModel;
import com.mouzetech.mouzefood.api.v1.model.EstadoModel;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;
import com.mouzetech.mouzefood.api.v1.model.GrupoModel;
import com.mouzetech.mouzefood.api.v1.model.PedidoResumoModel;
import com.mouzetech.mouzefood.api.v1.model.PermissaoModel;
import com.mouzetech.mouzefood.api.v1.model.ProdutoModel;
import com.mouzetech.mouzefood.api.v1.model.RestauranteResumoModel;
import com.mouzetech.mouzefood.api.v1.model.UsuarioModel;
import com.mouzetech.mouzefood.openapi.model.CidadeModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.CozinhaModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.EstadoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.FormasPagamentoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.GrupoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.LinksModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.PageableModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.PedidoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.PermissaoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.ProblemaNotFoundOpenApi;
import com.mouzetech.mouzefood.openapi.model.ProdutoModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.RestauranteModelOpenApi;
import com.mouzetech.mouzefood.openapi.model.UsuarioModelOpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(value = BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mouzetech.mouzefood.api"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.additionalModels(typeResolver.resolve(ProblemaNotFoundOpenApi.class))
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, CozinhaModel.class), CozinhaModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModel.class), PedidoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CidadeModel.class), CidadeModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, EstadoModel.class), EstadoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, GrupoModel.class), GrupoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, UsuarioModel.class), UsuarioModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, RestauranteResumoModel.class), RestauranteModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProdutoModel.class), ProdutoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissaoModel.class), PermissaoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(ResponseEntity.class, FormaPagamentoModel.class), FormasPagamentoModelOpenApi.class))
//				.globalRequestParameters(Collections.singletonList(
//						new RequestParameterBuilder()
//							.name("campos")
//							.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
//							.in(ParameterType.QUERY)
//							.required(true)
//							.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//		                    .build()
//						))
				.ignoredParameterTypes(ServletWebRequest.class)
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"))
				.tags(new Tag("Cozinhas", "Gerencia as cozinhas"))
				.tags(new Tag("Grupos", "Gerencia os grupos"))
				.tags(new Tag("FormasPagamento", "Gerencia as formas de pagamento"))
				.tags(new Tag("Pedidos", "Gerencia os pedidos"))
				.tags(new Tag("Restaurantes", "Gerencia os restaurantes"))
				.tags(new Tag("Estados", "Gerencia os estados"))
				.tags(new Tag("Produtos", "Gerencia os produtos"))
				.tags(new Tag("Usuários", "Gerencia os usuários"))
				.tags(new Tag("Estatísticas", "Gera dados estatísticos"))
				.tags(new Tag("Permissões", "Gerencia as permissões"));
	}
	
	private List<Response> globalGetResponseMessages(){
		return Arrays.asList(
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
					
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						.description("O tipo da representação do recurso retornado não é compatível com o esperado pelo requisitante")
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_FOUND.value()))
						.description("Recurso inexistente")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaNotFoundModelReference())
						.build()
				);
	}
	
	private List<Response> globalPostResponseMessages(){
		return Arrays.asList(
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
					
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						.description("O tipo da representação do recurso retornado não é compatível com o esperado pelo requisitante")
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
						.description("O corpo da requisição não está no formato esperado pelo servidor.")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						.description("Houve um erro no processamento da requisição por culpa do requisitante")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build()
				);
	}
	
	private List<Response> globalPutResponseMessages(){
		return Arrays.asList(
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
					
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						.description("O tipo da representação do recurso retornado não é compatível com o esperado pelo requisitante")
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
						.description("O corpo da requisição não está no formato esperado pelo servidor.")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						.description("Houve um erro no processamento da requisição por culpa do requisitante")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_FOUND.value()))
						.description("Recurso inexistente")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build()
				);
	}
	
	private List<Response> globalDeleteResponseMessages(){
		return Arrays.asList(
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						.description("Houve um erro no processamento da requisição por culpa do requisitante")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build(),
						
					new ResponseBuilder()
						.code(String.valueOf(HttpStatus.NOT_FOUND.value()))
						.description("Recurso inexistente")
						.representation(MediaType.APPLICATION_JSON)
						.apply(getProblemaModelReference())
						.build()
				);
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("MouzeFood API")
				.description("API para venda de produtos de restaurantes")
				.contact(new Contact("MouzeTech", "https://www.mouzetech.com.br", "contato@mouzetech.com.br"))
				.version("1")
				.build();
	}
	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.mouzetech.mouzefood.api.exceptionhandler")))));
	}
	
	private Consumer<RepresentationBuilder> getProblemaNotFoundModelReference() {
	    return r -> r.model(m -> m.name("ProblemaNotFoundOpenApi")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("ProblemaNotFoundOpenApi").namespace("com.mouzetech.mouzefood.openapi.model")))));
	}
	
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
}