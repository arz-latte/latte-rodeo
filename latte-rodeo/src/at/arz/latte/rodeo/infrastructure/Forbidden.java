package at.arz.latte.rodeo.infrastructure;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ApiException;

public class Forbidden
		extends ApiException {
	private static final long serialVersionUID = 1L;

	@Override
	public Response createResponse(Locale locale) {
		return Response.status(Status.FORBIDDEN).build();
	}
}
