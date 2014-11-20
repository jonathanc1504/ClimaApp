package org.clima.rest;

import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author John
 * 
 */
public class RestClimaJaxRsApplication extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public RestClimaJaxRsApplication() {
		
        packages("org.clima.rest");
		register(EntityFilteringFeature.class);
	}
}
