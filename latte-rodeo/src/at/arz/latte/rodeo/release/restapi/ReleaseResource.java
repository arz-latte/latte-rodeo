package at.arz.latte.rodeo.release.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;
import at.arz.latte.rodeo.release.admin.CreateRelease;

@Path("/releases")
@Produces(MediaType.APPLICATION_JSON)
public class ReleaseResource {
	
	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public ListReleases listReleases() {
		ListReleasesQuery query = new ListReleasesQuery();
		return model.query(query);
	}

	@Path("/{releaseName}")
	@GET
	public ReleaseName getRelease(@PathParam("releaseName") ReleaseName releaseName) {
		FindReleaseByNameQuery query = new FindReleaseByNameQuery();
		query.setReleaseName(releaseName);
		Release release = model.query(query);
		return release.getReleaseName();
	}

	@Path("/{releaseName}")
	@PUT
	public void execute(@PathParam("releaseName") ReleaseName releaseName, CreateRelease command) {
		model.execute(command);
	}

	@Path("/{releaseName}/applications")
	@GET
	public ListApplications getAllApplicationsByRelease(@PathParam("releaseName") ReleaseName releaseName) {
		FindReleaseByNameQuery query = new FindReleaseByNameQuery();
		query.setReleaseName(releaseName);
		Release release = model.query(query);

		ListApplicationsByReleaseQuery query2 = new ListApplicationsByReleaseQuery();
		query2.setRelease(release);
		return model.query(query2);
	}
}
