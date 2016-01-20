package at.arz.latte.rodeo.execution.restapi;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobsResult {

	private List<JobStatusResult> jobs;

	protected JobsResult() {
		// tool constructor
	}

	public JobsResult(List<JobStatusResult> jobs) {
		this.jobs = jobs;
	}

	public List<JobStatusResult> getJobs() {
		if (jobs == null) {
			return Collections.emptyList();
		}
		return jobs;
	}

}
