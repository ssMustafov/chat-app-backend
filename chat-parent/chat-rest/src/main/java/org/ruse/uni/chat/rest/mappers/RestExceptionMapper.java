package org.ruse.uni.chat.rest.mappers;

import java.lang.invoke.MethodHandles;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.ruse.uni.chat.rest.exceptions.RestError;
import org.ruse.uni.chat.rest.exceptions.RestException;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public Response toResponse(RestException exception) {
		RestError error = exception.getError();
		LOGGER.errorf("Rest exception: {}, {}", error.getStatus(), error.getErrorMessage(), exception);
		return Response.status(error.getStatus()).entity(error.getErrorMessage()).build();
	}

}
