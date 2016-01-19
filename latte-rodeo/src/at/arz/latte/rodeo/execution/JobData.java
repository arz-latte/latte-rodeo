package at.arz.latte.rodeo.execution;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.rodeo.pipeline.StepName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobData {

	private StepName stepName;

	private String path;

	private List<JobAttribute> attributes;

	protected JobData() {
		// tool constructor
	}

	public JobData(StepName stepName, String path, List<JobAttribute> attributes) {
		this.stepName = stepName;
		this.path = path;
		this.attributes = attributes;
	}

	public List<JobAttribute> getAttributes() {
		if (attributes == null) {
			return Collections.emptyList();
		}
		return attributes;
	}

	public String getPath() {
		return path;
	}

	public StepName getStepName() {
		return stepName;
	}

	@Override
	public String toString() {
		return "JobData [stepName=" + stepName + ", path=" + path + ", attributes=" + attributes + "]";
	}

}
