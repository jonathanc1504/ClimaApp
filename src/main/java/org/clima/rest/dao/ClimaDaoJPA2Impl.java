package org.clima.rest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ClimaDaoJPA2Impl implements ClimaDao {

	@PersistenceContext(unitName="climaRestPersistence")
	private EntityManager entityManager;

	@Override
	public List<ClimaEntity> getClimasByEstado(String estado) {
		String sqlString = null;
		if(estado!=null){
			sqlString ="SELECT p FROM ClimaEntity p WHERE p.estado = ?1";
			TypedQuery<ClimaEntity> query = entityManager.createQuery(sqlString, ClimaEntity.class);		
			query.setParameter(1, estado);
			return query.getResultList();
		}
		return null;
		
	}

	@Override
	public ClimaEntity getClimaByDia(Integer dia) {
		String sqlString = null;
		if(dia!=null){
			sqlString ="SELECT p FROM ClimaEntity p WHERE p.dia = ?1";
			TypedQuery<ClimaEntity> query = entityManager.createQuery(sqlString, ClimaEntity.class);		
			query.setParameter(1, dia);
			return query.getSingleResult();
		}
		return null;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Integer createClima(ClimaEntity clima) {
		System.out.print("createClima> "+ clima);
		entityManager.persist(clima);
		System.out.print("createClima> after merge"+ clima);
		entityManager.flush();
		System.out.print("createClima> after flush"+ clima);
		return clima.getId();
	}

	@Override
	public void deleteClimas() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE clima");		
		query.executeUpdate();
		
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void updateClima(ClimaEntity clima){
		System.out.print("updateClima> "+ clima);
		entityManager.merge(clima);
		System.out.print("createClima> after merge"+ clima);
	}
	

}
