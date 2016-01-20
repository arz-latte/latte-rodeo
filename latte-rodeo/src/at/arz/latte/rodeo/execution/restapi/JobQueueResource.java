package at.arz.latte.rodeo.execution.restapi;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import at.arz.latte.rodeo.execution.JobsByStatus;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/jobqueue")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class JobQueueResource {

	@Inject
	private RodeoModel model;

	@Inject
	private Workspace workspace;

	@Path("/")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public JobsResult listQueuedJobs() {
		List<JobStatusResult> list = model.applyAll(new JobsByStatus(), new MapJobToJobStatusResult());
		return new JobsResult(list);
	}
}

