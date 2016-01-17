package at.arz.latte.rodeo.pipeline.restapi;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.api.ObjectNotFound;
import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.FindSteps;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepName;
import at.arz.latte.rodeo.pipeline.admin.CreateStep;

@Path("/steps")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class StepResource {

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

	@Path("/")
	@PUT
	public void createStep(CreateStep command) {
		model.execute(command);
	}

	@Path("/{name}")
	@GET
	public StepDetail getDetail(@PathParam("name") StepName name) {
		RodeoFunction<Step, StepDetail> function = new RodeoFunction<Step, StepDetail>() {

			@Override
			public StepDetail apply(Step input) {
				return new StepDetail(input.getName(), input.getConfiguration());
			}
		};
		List<StepDetail> list = model.applyAll(new FindSteps(name), function);
		if (list.isEmpty()) {
			throw new ObjectNotFound(Step.class, name);
		}
		return list.get(0);
	}
}
