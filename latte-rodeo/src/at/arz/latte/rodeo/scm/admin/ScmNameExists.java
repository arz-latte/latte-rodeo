package at.arz.latte.rodeo.scm.admin;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ApiException;
import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.scm.ScmName;

@ApplicationException(rollback = true)
public class ScmNameExists
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private ScmName name;

	public ScmNameExists(ScmName name) {
		super(name.toString());
		this.name = name;
	}

	public ScmName getName() {
		return name;
	}
	
	@Override
	public Response createResponse() {
		return Response.status(Status.CONFLICT).entity(buildErrorDetail()).build();
	}
	
	private ErrorDetail buildErrorDetail() {
		return new ErrorDetail(this.getClass().getSimpleName(), name.toString());
	}

}
