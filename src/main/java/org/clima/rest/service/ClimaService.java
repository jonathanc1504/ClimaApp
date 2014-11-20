package org.clima.rest.service;

import java.util.List;

import org.clima.rest.errorhandling.AppException;
import org.clima.rest.errorhandling.CustomReasonPhraseException;
import org.clima.rest.resource.Clima;


public interface ClimaService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	public Integer createClima(Clima clima) throws AppException;
		
	/*
	 ******************** Read related methods ********************
	  */ 	
	
	public List<Clima> getClimasByEstado(String estado) throws AppException;

	
	public Clima getClimaByDia(Integer dia) throws AppException;

	/*
	 * ******************** Delete related methods **********************
	 * */
	/** removes all climas */
	public void deleteClimas();
	
	public void calculateClima() throws AppException;

	/**
	 * Empty method generating a Business Exception
	 * @throws CustomReasonPhraseException
	 */
	public void generateCustomReasonPhraseException() throws CustomReasonPhraseException;

}
