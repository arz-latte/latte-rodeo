package at.arz.latte.rodeo.pipeline.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.pipeline.StepName;

@XmlAccessorType(XmlAccessType.FIELD)
public class PipelineStepItem {

	private boolean optional;
	private StepName name;

	protected PipelineStepItem() {
		// tool constructor
	}

	public PipelineStepItem(StepName name, boolean optional) {
		this.name = name;
		this.optional = optional;
	}

	public StepName getName() {
		return name;
	}

	public boolean isOptional() {
		return optional;
	}

}
