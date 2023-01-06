package com.mouzetech.mouzefood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "foto_produto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class FotoProduto {
	
	@Id
	@Column(name = "produto_id")
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne
	@MapsId
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
	
	public Long getRestauranteId() {
		if(getProduto() != null) {
			return getProduto().getRestaurante().getId();
		}
		return null;
	}
}