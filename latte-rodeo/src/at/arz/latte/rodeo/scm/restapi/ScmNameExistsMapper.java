package at.arz.latte.rodeo.scm.restapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.scm.admin.ScmNameExists;

@Provider
public class ScmNameExistsMapper
		implements ExceptionMapper<ScmNameExists> {

	@Override
	public Response toResponse(ScmNameExists exception) {
		return Response.status(Status.CONFLICT).entity(buildErrorDetail(exception)).build();
	}

	private ErrorDetail buildErrorDetail(ScmNameExists exception) {
		return new ErrorDetail(exception.getClass().getSimpleName(), exception.getName());
	}
}
