package org.ruse.uni.chat.rest;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.ruse.uni.chat.rest.annotations.PublicRest;
import org.ruse.uni.chat.rest.filters.AuthenticationFilter;

/**
 *
 * @author sinan
 */
@Provider
public class PublicRestFeature implements DynamicFeature {

	@Inject
	private AuthenticationFilter authenticationFilter;

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		if (!resourceInfo.getResourceMethod().isAnnotationPresent(PublicRest.class)) {
			context.register(authenticationFilter);
		}
	}

}
