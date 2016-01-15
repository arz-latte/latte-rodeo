package at.arz.latte.rodeo.project.restapi;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import at.arz.latte.rodeo.api.ErrorDetail;
import at.arz.latte.rodeo.project.admin.ProjectExists;

@Provider
public class ProjectExistsMapper
		implements ExceptionMapper<ProjectExists> {

	@Override
	public Response toResponse(ProjectExists exception) {
		return Response.status(Status.CONFLICT)
						.entity(new ErrorDetail(ProjectExists.class.getSimpleName(), exception.getName()))
						.build();
	}
}
