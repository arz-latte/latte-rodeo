package at.arz.latte.rodeo.pipeline.restapi;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.api.ObjectNotFound;
import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.infrastructure.RodeoSecurity;
import at.arz.latte.rodeo.pipeline.FindSteps;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepConfiguration;
import at.arz.latte.rodeo.pipeline.StepName;
import at.arz.latte.rodeo.pipeline.admin.CreateCommandLineStep;

@Path("/steps")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class StepResource {
	
	@Inject
	private RodeoSecurity security;

	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public StepItems list(@QueryParam("name") StepName name) {
		RodeoFunction<Step, StepItem> converter = new RodeoFunction<Step, StepItem>() {

			@Override
			public StepItem apply(Step step) {
				return new StepItem(step.getName());
			}
		};
		return new StepItems(model.applyAll(new FindSteps(name), converter));
	}

	@Path("{name}")
	@PUT
	public void createStep(@PathParam("name") StepName stepName, CreateCommandLineStep command) {
		security.assertUserIsAdmin();
		model.execute(command);
	}

	@Path("{name}")
	@DELETE
	public void removeStep(@PathParam("name") final StepName stepName) {
		security.assertUserIsAdmin();
		model.query(new RodeoQuery<Void>() {

			@Override
			public Void execute(EntityManager entityManager) {
				TypedQuery<Step> query = entityManager.createQuery(	"select o from Step o where o.name=:name",
																	Step.class);
				query.setParameter("name", stepName);
				Step step = query.getSingleResult();
				entityManager.remove(step);
				return null;
			}

		});
	}

	@Path("/{name}")
	@GET
	public StepDetail getDetail(@PathParam("name") StepName name) {
		RodeoFunction<Step, StepDetail> function = new RodeoFunction<Step, StepDetail>() {

			@Override
			public StepDetail apply(Step input) {
				StepConfiguration configuration = input.getConfiguration();
				return new StepDetail(input.getName(), configuration);
			}
		};
		List<StepDetail> list = model.applyAll(new FindSteps(name), function);
		if (list.isEmpty()) {
			throw new ObjectNotFound(Step.class, name);
		}
		return list.get(0);
	}
}
