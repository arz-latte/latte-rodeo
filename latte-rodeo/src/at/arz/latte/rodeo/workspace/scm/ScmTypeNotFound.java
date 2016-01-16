package at.arz.latte.rodeo.workspace.scm;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.ApiException;
import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.scm.ScmType;


public class ScmTypeNotFound
		extends ApiException {

	private static final long serialVersionUID = 1L;

	private ScmType type;

	public ScmTypeNotFound(ScmType type) {
		super(type.toString());
		this.type = type;
	}

	@Override
	public Response createResponse() {
		return Response.status(Status.NOT_FOUND).entity(new ErrorDetail("scmType", type.toString())).build();
	}

}
