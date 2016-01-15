package at.arz.latte.rodeo.scm.restapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.scm.admin.ScmLocationExists;

@Provider
public class ScmLocationExistsMapper
		implements ExceptionMapper<ScmLocationExists> {

	@Override
	public Response toResponse(ScmLocationExists exception) {
		System.out.println("HANDLE SCM REPOSITORY EXISTS");
		return Response.status(Status.CONFLICT).entity(buildErrorDetail(exception)).build();
	}

	private ErrorDetail buildErrorDetail(ScmLocationExists exception) {
		return new ErrorDetail(ScmLocationExists.class.getSimpleName(), exception.getLocation().toString());
	}
}
