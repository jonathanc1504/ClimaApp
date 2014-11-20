package org.cllima.rest.service.integration;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.clima.rest.resource.Clima;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

public class ClimaServiceIT {

	@Test
	public void testGetClima() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8888/climaApp/clima/2");

		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		Clima clima = response.readEntity(Clima.class);

		ObjectMapper mapper = new ObjectMapper();
		System.out
				.print("Received Clima from database *************************** "
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(clima));

	}
	
	
}
