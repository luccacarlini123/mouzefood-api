package com.mouzetech.mouzefood.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private DataSize tamanhoMaximoArquivo;
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		this.tamanhoMaximoArquivo = DataSize.parse(constraintAnnotation.max());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return value == null || value.getSize() <= tamanhoMaximoArquivo.toBytes();
	}

}
