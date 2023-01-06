package com.mouzetech.mouzefood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mouzetech.mouzefood.core.validator.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotBlank
	@Column(nullable = false)
	private String nome;
	
//	@NotNull
//	@PositiveOrZero
	//@Multiplo(numero = 5)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@JoinColumn(name = "cozinha_id", nullable = false)
	@ManyToOne
	private Cozinha cozinha;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	
	@Embedded
	private Endereco endereco;
	
	@OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
	private List<Produto> produtos = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", 
	joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	public void desassociarFormaPagamento(FormaPagamento formaPagamento) {
		getFormasPagamento().remove(formaPagamento);
	}
	
	public void associarFormaPagamento(FormaPagamento formaPagamento) {
		getFormasPagamento().add(formaPagamento);
	}
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void desativar() {
		setAtivo(false);
	}

	public void abrir() {
		setAberto(true);
	}
	
	public void fechar() {
		setAberto(false);
	}
	
	public boolean aberto() {
		return getAberto();
	}
	
	public boolean fechado() {
		return !aberto();
	}
	
	public boolean ativo() {
		return getAtivo();
	}
	
	public boolean inativo() {
		return !ativo();
	}

	public void adicionarResponsavel(Usuario usuario) {
		getResponsaveis().add(usuario);		
	}
	
	public void removerResponsavel(Usuario usuario) {
		getResponsaveis().remove(usuario);		
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
		return getFormasPagamento().contains(formaPagamento);
	}
	
	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
		return !getFormasPagamento().contains(formaPagamento);
	}
	
	public boolean possuiProduto(Produto produto) {
		return getProdutos().contains(produto);
	}
	
	public boolean naoPossuiProduto(Produto produto) {
		return !getProdutos().contains(produto);
	}
}