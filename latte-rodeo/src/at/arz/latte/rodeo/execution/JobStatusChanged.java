package at.arz.latte.rodeo.execution;

public class JobStatusChanged {

	private Job.Status status;
	private JobIdentifier identifier;

	public JobStatusChanged(JobIdentifier identifier, Job.Status status) {
		this.identifier = identifier;
		this.status = status;
	}

	public Job.Status getStatus() {
		return status;
	}

	public JobIdentifier getIdentifier() {
		return identifier;
	}

}
