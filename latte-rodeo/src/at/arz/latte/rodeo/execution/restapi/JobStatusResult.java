package at.arz.latte.rodeo.execution.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.execution.JobIdentifier;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobStatusResult {

	private JobIdentifier identifier;
	private Job.Status status;

	public JobStatusResult() {
		// Tool constructor
	}

	public JobStatusResult(JobIdentifier identifier, Status status) {
		this.identifier = identifier;
		this.status = status;
	}

	public Job.Status getStatus() {
		return status;
	}

	public JobIdentifier getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		return "JobStatusResult [identifier=" + identifier + ", status=" + status + "]";
	}

}
