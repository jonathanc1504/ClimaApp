package org.clima.rest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class PlanetaDaoJPA2Impl implements PlanetaDao {

	@PersistenceContext(unitName="climaRestPersistence")
	private EntityManager entityManager;


	@Override
	public List<PlanetaEntity> getPlanetas() {
		String sqlString = "SELECT p FROM PlanetaEntity p";
		TypedQuery<PlanetaEntity> query = entityManager.createQuery(sqlString, PlanetaEntity.class);		
		return query.getResultList();
	}

	@Override
	public PlanetaEntity getPlanetaByNombre(String nombre) {
		String sqlString = null;
		if(nombre!=null){
			sqlString ="SELECT p FROM PlanetaEntity p WHERE p.nombre = ?1";
			TypedQuery<PlanetaEntity> query = entityManager.createQuery(sqlString, PlanetaEntity.class);		
			query.setParameter(1, nombre);
			return query.getSingleResult();
		}
		return null;
		
	}
}
