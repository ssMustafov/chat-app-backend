package org.ruse.uni.chat.rest.mappers;

import java.lang.invoke.MethodHandles;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.ruse.uni.chat.rest.exceptions.RestError;

/**
 * Default exception mapper.
 *
 * @author sinan
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public Response toResponse(Throwable exception) {
		LOGGER.error("General exception", exception);

		// TODO: extract label
		RestError error = new RestError(Status.INTERNAL_SERVER_ERROR,
				"Error occurred. Please contact your administrator.", exception);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
	}

}
