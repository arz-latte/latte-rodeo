package at.arz.latte.rodeo.client;

import java.util.List;

import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.JobIdentifier;
import at.arz.latte.rodeo.execution.restapi.JobData;
import at.arz.latte.rodeo.execution.restapi.JobStatusResult;
import at.arz.latte.rodeo.execution.restapi.JobsResult;

public class JobClient {

	private static final String API_JOBS = "api/jobs";
	private static final String API_JOBQUEUE = "api/jobqueue";

	private RodeoClient client;

	public JobClient(RodeoClient client) {
		this.client = client;
	}

	public void execute(JobIdentifier identifier, JobData data) {
		client.begin(API_JOBS, identifier.toString()).put(data);
	}

	public List<JobStatusResult> getAllJobStates() {
		return client.begin(API_JOBS).get(JobsResult.class).getJobs();
	}

	public List<JobStatusResult> getJobsByStatus(Job.Status status) {
		return client.resource(API_JOBS)
						.queryParam("status", status.name())
						.accept(client.mediaType())
						.get(JobsResult.class)
						.getJobs();
	}

	public JobStatusResult getJobStatus(JobIdentifier id) {
		return client.begin(API_JOBS, id.toString()).get(JobStatusResult.class);
	}

	public List<JobStatusResult> getAllQueuedJobStates() {
		return client.begin(API_JOBQUEUE).get(JobsResult.class).getJobs();
	}

}
