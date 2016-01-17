package at.arz.latte.rodeo.scm.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.infrastructure.RodeoSecurity;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.ScmName;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.scm.admin.CreateScm;

@Path("/repositories")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ScmResource {

	@Inject
	private RodeoSecurity security;

	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public GetScmRepositoriesResult listRepositories(	@QueryParam("type") ScmType type,
														@QueryParam("location") ScmLocation location,
														@QueryParam("name") ScmName name) {
		return model.query(new FindScmRepositories().with(type).with(location).with(name));
	}

	@Path("{name}")
	@GET
	public GetScmRepositoriesResult getRepositoryByName(@PathParam("name") ScmName name) {
		return model.query(new FindScmRepositories().with(name));
	}

	@Path("/")
	@PUT
	public void execute(CreateScm command) {
		security.assertUserIsAdmin();
		model.execute(command);
	}

}
