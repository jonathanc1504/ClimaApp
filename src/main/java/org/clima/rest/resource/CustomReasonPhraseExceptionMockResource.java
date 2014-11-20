package org.clima.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.clima.rest.errorhandling.CustomReasonPhraseException;
import org.clima.rest.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/mocked-custom-reason-phrase-exception")
public class CustomReasonPhraseExceptionMockResource {
	
	@Autowired
	private ClimaService climaService;
	
	@GET
	public void testReasonChangedInResponse() throws CustomReasonPhraseException{
		climaService.generateCustomReasonPhraseException();
	}
}
