package com.mouzetech.mouzefoodapi.domain.infrastructure.repository;

import static com.mouzetech.mouzefoodapi.domain.infrastructure.specification.RestauranteSpecs.comFreteGratis;
import static com.mouzetech.mouzefoodapi.domain.infrastructure.specification.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteQueries;
import com.mouzetech.mouzefoodapi.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		var jpql = new StringBuilder("from Restaurante where 0 = 0 ");
		var parametros = new HashMap<String, Object>();
		
		
		if(StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if(taxaInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaInicial);
		}
		
		if(taxaFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFinal);
		}
		
		TypedQuery<Restaurante> typedQuery = entityManager.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> typedQuery.setParameter(chave, valor));
				
		return typedQuery.getResultList();
		
	}

	@Override
	public List<Restaurante> findCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if(taxaInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		
		if(taxaFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteria);
		return query.getResultList();
		
	}
	
	@Override
	public List<Restaurante> findComFreteGratis(String nome){
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	
}
