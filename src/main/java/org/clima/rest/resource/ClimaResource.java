package org.clima.rest.resource;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.clima.rest.errorhandling.AppException;
import org.clima.rest.interceptors.Compress;
import org.clima.rest.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author Johncoder
 * 
 */
@Component
@Path("/clima")
public class ClimaResource {

	@Autowired
	private ClimaService climaService;

	/**
	 * Returns estado de clima para un dia.
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws AppException
	 */
	@GET
	@Compress
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getClimaByDia(
			@QueryParam("dia") Integer dia)
			throws IOException,	AppException {
		Clima clima = climaService.getClimaByDia(dia);
		//climaService.calculateClima();
		//Clima clima = new Clima();
		return Response.status(200)
				.entity(clima, new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}


	@POST
	@Compress
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response calculateClima()
			throws IOException,	AppException {
		climaService.calculateClima();
		
		return Response.status(200)
				.entity("Proceso de Calculo finalizado OK", new Annotation[0])
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	
	public void setClimaService(ClimaService climaService) {
		this.climaService = climaService;
	}



}
