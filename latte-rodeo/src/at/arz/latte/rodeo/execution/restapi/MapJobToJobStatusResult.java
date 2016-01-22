package at.arz.latte.rodeo.execution.restapi;

import java.net.URI;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.execution.Job;

public final class MapJobToJobStatusResult
		implements RodeoFunction<Job, JobStatusResult> {

	private URI baseURI;

	public MapJobToJobStatusResult(URI baseURI) {
		this.baseURI = baseURI;
	}

	@Override
	public JobStatusResult apply(Job job) {
		String link = baseURI.toString() + "jobs/" + job.getIdentifier().toString() + "/log.txt";

		return new JobStatusResult(job.getIdentifier(), job.getStatus(), link);
	}
}