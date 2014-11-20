package org.clima.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.clima.rest.dao.PlanetaDao;
import org.clima.rest.dao.PlanetaEntity;
import org.clima.rest.errorhandling.AppException;
import org.clima.rest.resource.Planeta;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	PlanetaDao planetaDao;
		



	@Override
	public List<Planeta> getPlanetas() throws AppException {
		List<PlanetaEntity> planetas = planetaDao.getPlanetas();
		
		return getPlanetasFromEntities(planetas);
	}
	
	public Planeta getPlanetaByNombre(String nombre) throws AppException{
		PlanetaEntity planeta = planetaDao.getPlanetaByNombre(nombre);
		return new Planeta(planeta);
		
	}
	

	private List<Planeta> getPlanetasFromEntities(List<PlanetaEntity> planetaEntities) {
		List<Planeta> response = new ArrayList<Planeta>();
		for(PlanetaEntity planetaEntity : planetaEntities){
			response.add(new Planeta(planetaEntity));					
		}
		
		return response;
	}
}
