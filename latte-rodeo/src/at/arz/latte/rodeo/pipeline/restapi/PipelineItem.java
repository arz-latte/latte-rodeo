package at.arz.latte.rodeo.pipeline.restapi;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.pipeline.PipelineName;

@XmlAccessorType(XmlAccessType.FIELD)
public class PipelineItem {

	private PipelineName name;
	private List<PipelineStepItem> steps;

	protected PipelineItem() {
		// tool constructor
	}

	public PipelineItem(PipelineName name, List<PipelineStepItem> steps) {
		this.name = name;
		this.steps = steps;
	}

	public PipelineName getName() {
		return name;
	}
	
	public List<PipelineStepItem> getSteps() {
		return Collections.unmodifiableList(steps);
	}
}
