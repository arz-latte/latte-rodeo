package at.arz.latte.rodeo.pipeline.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.infrastructure.RodeoSecurity;
import at.arz.latte.rodeo.pipeline.FindPipelines;
import at.arz.latte.rodeo.pipeline.Pipeline;
import at.arz.latte.rodeo.pipeline.PipelineName;
import at.arz.latte.rodeo.pipeline.PipelineStep;
import at.arz.latte.rodeo.pipeline.admin.CreatePipeline;

@Path("/pipelines")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class PipelineResource {
	
	@Inject
	private RodeoSecurity security;

	@Inject
	private RodeoModel model;

	@Path("/")
	@GET
	public PipelineItems list(@QueryParam("name") PipelineName name) {
		RodeoFunction<Pipeline, PipelineItem> converter = new RodeoFunction<Pipeline, PipelineItem>() {

			@Override
			public PipelineItem apply(Pipeline pipeline) {
				ArrayList<PipelineStepItem> stepItems = new ArrayList<PipelineStepItem>();
				List<PipelineStep> steps = pipeline.getPipelineSteps();
				for (PipelineStep pipelineStep : steps) {
					stepItems.add(new PipelineStepItem(pipelineStep.getStep().getName(), pipelineStep.isOptional()));
				}
				return new PipelineItem(pipeline.getName(), stepItems);
			}
		};
		return new PipelineItems(model.applyAll(new FindPipelines(name), converter));
	}

	@Path("/")
	@PUT
	public void createPipeline(CreatePipeline command) {
		security.assertUserIsAdmin();
		model.execute(command);
	}

}
