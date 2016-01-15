package at.arz.latte.rodeo.scm.admin;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ApiException;
import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.scm.ScmLocation;

@ApplicationException(rollback = true)
public class ScmLocationExists
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private ScmLocation location;

	public ScmLocationExists(ScmLocation location) {
		super(location.toString());
		this.location = location;
	}

	public ScmLocation getLocation() {
		return location;
	}

	@Override
	public Response createResponse() {
		return Response.status(Status.CONFLICT).entity(buildErrorDetail()).build();
	}
	
	private ErrorDetail buildErrorDetail() {
		return new ErrorDetail(this.getClass().getSimpleName(), location.toString());
	}

	
	
}
