package at.arz.latte.rodeo.scm.admin;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ApiException;
import at.arz.latte.rodeo.api.ErrorDetail;

@ApplicationException(rollback = true)
public class ScmNameExists
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private String name;

	public ScmNameExists(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public Response createResponse() {
		return Response.status(Status.CONFLICT).entity(buildErrorDetail()).build();
	}
	
	private ErrorDetail buildErrorDetail() {
		return new ErrorDetail(this.getClass().getSimpleName(), name);
	}

}
