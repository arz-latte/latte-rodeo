package at.arz.latte.rodeo.infrastructure;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.rodeo.api.ApiException;

@Provider
public class ApiExceptionMapper
		implements ExceptionMapper<ApiException> {

	@Override
	public Response toResponse(ApiException exception) {
		return exception.createResponse();
	}
}
