package com.mouzetech.mouzefoodapi.core.validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PrecoMaiorQueZeroValidator implements ConstraintValidator<PrecoMaiorQueZero, BigDecimal> {
	
	@Override
	public boolean isValid(BigDecimal preco, ConstraintValidatorContext context) {
		return preco != null  && preco.doubleValue() > 0;
	}

}
