package at.arz.latte.rodeo.release.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.domain.RodeoModel;

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
	public String getRelease(@PathParam("releaseName") String release) {
		return release;
	}

}
