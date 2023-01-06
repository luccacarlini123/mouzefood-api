package com.mouzetech.mouzefood.core.validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		var valido = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), valorField).getReadMethod().invoke(object);
			String descricao = (String) BeanUtils.getPropertyDescriptor(object.getClass(), descricaoField).getReadMethod().invoke(object);
			if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
			}
			return valido;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}
