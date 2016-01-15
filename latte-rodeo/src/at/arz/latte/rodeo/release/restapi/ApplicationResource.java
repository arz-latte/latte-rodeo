package at.arz.latte.rodeo.release.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;

@Path("/applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource {
	
	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public ListApplications getAllApplications() {
		ListApplicationsQuery query = new ListApplicationsQuery();
		return model.query(query);
	}

	@Path("/{applicationName}")
	@GET
	public String getApplications(@PathParam("applicationName") String applicationName) {
		return applicationName;
	}

}
