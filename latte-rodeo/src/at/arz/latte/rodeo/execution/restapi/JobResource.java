package at.arz.latte.rodeo.execution.restapi;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.execution.FindJobs;
import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.JobAttribute;
import at.arz.latte.rodeo.execution.JobData;
import at.arz.latte.rodeo.execution.JobEngine;
import at.arz.latte.rodeo.execution.JobIdentifier;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/jobs")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class JobResource {

	@Inject
	private JobEngine engine;

	@Inject
	private RodeoModel model;

	@Inject
	private Workspace workspace;

	@Path("/{identifier}")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void submitJob(@PathParam("identifier") JobIdentifier identifier, JobData data) {
		Properties properties = new Properties();
		System.out.println(data);

		for (JobAttribute attribute : data.getAttributes()) {
			properties.setProperty(attribute.getName(), attribute.getValue());
		}
		File workspaceDir = workspace.getWorkspaceDir();
		File file = new File(workspaceDir, data.getPath());
		engine.submit(identifier, data.getStepName(), file, properties);
	}

	@Path("/")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public JobsResult listJobs(@QueryParam("status") Job.Status status) {
		List<JobStatusResult> list = model.applyAll(new FindJobs(new JobIdentifier("%"), status),
													new RodeoFunction<Job, JobStatusResult>() {

														@Override
														public JobStatusResult apply(Job job) {
															return new JobStatusResult(	job.getIdentifier(),
																						job.getStatus());
														}

													});
		return new JobsResult(list);
	}

	@Path("{identifier}")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response listJobs(@PathParam("identifier") JobIdentifier identifier) {
		List<JobStatusResult> list = model.applyAll(new FindJobs(identifier, null),
													new RodeoFunction<Job, JobStatusResult>() {

														@Override
														public JobStatusResult apply(Job job) {
															return new JobStatusResult(	job.getIdentifier(),
																						job.getStatus());
														}

													});
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND)
							.type(MediaType.TEXT_PLAIN)
							.entity(identifier + " not found.")
							.build();
		}
		return Response.ok(list.get(0)).build();
	}

}
