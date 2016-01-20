package at.arz.latte.rodeo.project.restapi;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.project.admin.CreateProject;

@Path("/projects")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ProjectResource {

	@Inject
	private RodeoModel model;

	@Path("/")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void execute(CreateProject command) {
		model.execute(command);
	}

}
