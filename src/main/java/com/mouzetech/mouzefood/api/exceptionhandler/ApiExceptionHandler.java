package com.mouzetech.mouzefood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.mouzetech.mouzefood.domain.exception.EntidadeEmUsoException;
import com.mouzetech.mouzefood.domain.exception.EntidadeNaoEncontradaException;
import com.mouzetech.mouzefood.domain.exception.NegocioException;
import com.mouzetech.mouzefood.domain.exception.ValidacaoException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_DADOS_INVALIDOS = "Há campos que estão inválidos, corrija-os e tente novamente.";
	private static final String MSG_ERRO_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente mais tarde e se o erro persistir,"
			+ " entre em contato com o administrador do sistema";
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if(rootCause instanceof PropertyBindingException) {
			return  handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail = String.format("O recurso %s, que você tentou acessar, não existe", ex.getRequestURL());
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é um tipo inválido. Corrija e informe um valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' não existe para o tipo %s", path, ex.getReferringClass().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, request);
		}
		
		return handleTypeMismatchException(ex, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
		String detail = String.format("O parâmtro de URL '%s' recebeu o valor '%s', que é de um tipo inválido."
				+ " Insira um valor com o tipo compatível a '%s'", ex.getParameter().getParameterName(), ex.getValue(), ex.getParameter().getParameterType().getSimpleName());
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		
		Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex, WebRequest request){
		String detail = "O parâmtro de URL é inválido";
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		Problem problem = createProblemBuilder(HttpStatus.BAD_REQUEST, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request){
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	public ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request){
		 String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		List<Problem.Object> problemObjects = bindingResult.getAllErrors()
				.stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problem.Object.builder()
							.objectName(name)
							.userMessage(message).build();
					
		}).collect(Collectors.toList());
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		Problem problem = createProblemBuilder(status, problemType, MSG_ERRO_DADOS_INVALIDOS)
				.detail(detail)
				.problemObjects(problemObjects)
				.userMessage(MSG_ERRO_DADOS_INVALIDOS)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleUntreatedExceptions(Exception ex, WebRequest request, HttpStatus status, HttpHeaders headers){
		String detail = MSG_ERRO_USUARIO_FINAL;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		
		ex.printStackTrace();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		HttpStatus status = HttpStatus.CONFLICT;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if(body == null) {
			body = Problem.builder()
			.status(status.value())
			.date(OffsetDateTime.now())
			.title(status.getReasonPhrase())
			.userMessage(MSG_ERRO_USUARIO_FINAL)
			.build();			
		}else if(body instanceof String) {
			body = Problem.builder()
					.status(status.value())
					.date(OffsetDateTime.now())
					.title((String)body)
					.userMessage(MSG_ERRO_USUARIO_FINAL)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
		return Problem.builder()
				.status(status.value())
				.date(OffsetDateTime.now())
				.title(problemType.getTitle())
				.type(problemType.getUri())
				.detail(detail);
	}
}