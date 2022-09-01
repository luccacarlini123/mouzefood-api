package com.mouzetech.mouzefoodapi.domain.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.mouzetech.mouzefoodapi.domain.repository.CustomJpaRepository;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
		implements CustomJpaRepository<T, ID>{

	private EntityManager entityManager;
	
	public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		String jpql = "from " + getDomainClass().getName();
		
		T entity = entityManager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);	
	}

	@Override
	public void detach(T entity) {
		entityManager.detach(entity);
	}
	
}
