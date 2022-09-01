package com.mouzetech.mouzefoodapi.core.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private String[] allowed;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.allowed = constraintAnnotation.allowed();
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || Arrays.stream(allowed).anyMatch(tipoPermitido -> tipoPermitido.equals(value.getContentType()));
	}

}
