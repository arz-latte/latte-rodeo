package at.arz.latte.rodeo.execution.restapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import at.arz.latte.rodeo.api.Attribute;
import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.JobEngine;
import at.arz.latte.rodeo.execution.JobIdentifier;
import at.arz.latte.rodeo.execution.JobsByIdentifierOrStatus;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.workspace.Workspace;

@Path("/jobs")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class JobResource {

	@Inject
	private JobEngine engine;

	@Context
	private ServletContext context;

	@Context
	private UriInfo uriInfo;

	@Inject
	private RodeoModel model;

	@Inject
	private Workspace workspace;

	@Path("/{identifier}")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void submitJob(@PathParam("identifier") JobIdentifier identifier, JobData data) {
		Properties properties = new Properties();
		for (Attribute attribute : data.getAttributes()) {
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
		JobsByIdentifierOrStatus query = new JobsByIdentifierOrStatus(null, status);
		List<JobStatusResult> list = model.applyAll(query, new MapJobToJobStatusResult(uriInfo.getBaseUri()));
		return new JobsResult(list);
	}

	@Path("{identifier}")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response listJobs(@PathParam("identifier") JobIdentifier identifier) {
		List<JobStatusResult> list = model.applyAll(new JobsByIdentifierOrStatus(identifier, null),
													new RodeoFunction<Job, JobStatusResult>() {

														@Override
														public JobStatusResult apply(Job job) {
															String link = uriInfo.getBaseUri().toString() + "jobs/"
																			+ job.getIdentifier()
																												.toString()
																			+ "/log.txt";
															return new JobStatusResult(	job.getIdentifier(),
																						job.getStatus(),
																						link);
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

	@Path("{identifier}/log.txt")
	@GET
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getJobLog(@PathParam("identifier") JobIdentifier identifier) {
		final List<Job> list = model.query(new JobsByIdentifierOrStatus(identifier, null));
		if (list.isEmpty()) {
			return respondFileNotFound(identifier);
		}
		String dir = list.get(0).getWorkDirectory();
		if (dir == null) {
			return respondFileNotFound(identifier);
		}
		File workDirectory = new File(dir);
		final File file = new File(workDirectory, "log.txt");
		if (!file.exists()) {
			return respondFileNotFound(identifier);
		}

		StreamingOutput output = new StreamingOutput() {

			@Override
			public void write(OutputStream out) throws IOException {
				byte[] buffer = new byte[80];
				try (FileInputStream inputStream = new FileInputStream(file)) {
					int rc = inputStream.read(buffer);
					while (rc != -1) {
						out.write(buffer, 0, rc);
						rc = inputStream.read(buffer);
					}
				}
			}
		};
		return Response.status(404).type(MediaType.TEXT_PLAIN).entity(output).build();

	}

	private Response respondFileNotFound(JobIdentifier identifier) {
		return Response.status(404).type(MediaType.TEXT_PLAIN).entity(identifier + " not found.").build();
	}

}
