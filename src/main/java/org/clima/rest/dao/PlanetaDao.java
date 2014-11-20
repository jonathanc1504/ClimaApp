package org.clima.rest.dao;

import java.util.List;

public interface PlanetaDao {

	public List<PlanetaEntity> getPlanetas();
	
	public PlanetaEntity getPlanetaByNombre(String nombre);
	
}
