package com.mouzetech.mouzefood.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data
public class Cidade {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotBlank
	private String nome;
	
//	@Valid
//	@NotNull
//	@ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
	@JoinColumn(nullable = false)
	@ManyToOne
	private Estado estado;
	
}
