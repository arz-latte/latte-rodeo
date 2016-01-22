package at.arz.latte.rodeo.execution.restapi;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.execution.JobIdentifier;
import at.arz.latte.rodeo.execution.JobsByIdentifierOrStatus;
import at.arz.latte.rodeo.execution.JobsByStatus;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/jobqueue")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class JobQueueResource {

	@Inject
	private RodeoModel model;

	@Context
	private UriInfo uriInfo;

	@Inject
	private Workspace workspace;

	@Path("/")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public JobsResult listQueuedJobs() {
		List<JobStatusResult> list = model.applyAll(new JobsByStatus(),
													new MapJobToJobStatusResult(uriInfo.getBaseUri()));
		return new JobsResult(list);
	}

	@Path("{identifier}/clearState")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response clearJobState(@PathParam("identifier") JobIdentifier identifier) {
		model.applyAll(new JobsByIdentifierOrStatus(identifier), new RodeoFunction<Job, Void>() {

			@Override
			public Void apply(Job input) {
				input.setStatus(Status.FAILED);
				return null;
			}
		});
		return Response.ok("state cleared", MediaType.TEXT_XML).build();
	}

}

