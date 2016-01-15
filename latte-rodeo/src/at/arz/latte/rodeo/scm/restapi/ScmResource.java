package at.arz.latte.rodeo.scm.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.scm.admin.CreateScm;

@Path("/repositories")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ScmResource {

	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public GetScmRepositoriesResult listRepositories() {
		return model.query(new GetScmRepositories());
	}
	
	@Path("/")
	@PUT
	public void execute(CreateScm command) {
		model.execute(command);
	}

}
