package at.arz.latte.rodeo.release.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationName;
import at.arz.latte.rodeo.release.admin.CreateApplication;

@Path("/applications")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
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
	public ApplicationName getApplications(@PathParam("applicationName") ApplicationName applicationName) {
		FindApplicationByNameQuery query = new FindApplicationByNameQuery();
		query.setApplicationName(applicationName);
		Application application = model.query(query);
		return application.getName();
	}

	@Path("/{applicationName}")
	@PUT
	public void execute(@PathParam("applicationName") ApplicationName applicationName, CreateApplication command) {
		model.execute(command);
	}

	@Path("/{applicationName}/releases")
	@GET
	public ListReleases getAllApplicationsByRelease(@PathParam("applicationName") ApplicationName applicationName) {
		FindApplicationByNameQuery query = new FindApplicationByNameQuery();
		query.setApplicationName(applicationName);
		Application application = model.query(query);

		ListReleasesByApplicationQuery query2 = new ListReleasesByApplicationQuery();
		query2.setApplication(application);
		return model.query(query2);
	}
}
