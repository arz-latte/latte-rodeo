package at.arz.latte.rodeo.execution.restapi;

import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.execution.Job;

public final class MapJobToJobStatusResult
		implements RodeoFunction<Job, JobStatusResult> {

	@Override
	public JobStatusResult apply(Job job) {
		return new JobStatusResult(job.getIdentifier(), job.getStatus());
	}
}