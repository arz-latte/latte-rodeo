package at.arz.latte.rodeo.project.restapi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.domain.RodeoModel;
import at.arz.latte.rodeo.project.Project;

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

	@Inject
	private RodeoModel model;

	@Path("/test")
	@GET
	public String sample(final String arg) {
		model.execute(new RodeoCommand() {

			@Override
			public void execute(EntityManager entityManager) {
				entityManager.persist(new Project("sample project"));
			}
		});
		return "success";
	}

}
