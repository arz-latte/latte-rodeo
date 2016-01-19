package at.arz.latte.rodeo.execution.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.execution.Job;
import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.execution.JobIdentifier;

@XmlAccessorType(XmlAccessType.FIELD)
public class JobStatusResult {

	private JobIdentifier identifier;
	private Job.Status status;

	public JobStatusResult() {
		// TODO Auto-generated constructor stub
	}

	public JobStatusResult(JobIdentifier identifier, Status status) {
		super();
		this.identifier = identifier;
		this.status = status;
	}

	@Override
	public String toString() {
		return "JobStatusResult [identifier=" + identifier + ", status=" + status + "]";
	}

}