package org.clima.rest.service;

import java.util.List;

import org.clima.rest.errorhandling.AppException;
import org.clima.rest.resource.Planeta;


public interface PlanetaService {
	
	public List<Planeta> getPlanetas() throws AppException;

	public Planeta getPlanetaByNombre(String nombre) throws AppException;
	
 }
