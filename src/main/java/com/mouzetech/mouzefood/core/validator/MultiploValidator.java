package com.mouzetech.mouzefood.core.validator;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		var valido = true;
		
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal);
			
			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
		
		return valido;
	}

}
