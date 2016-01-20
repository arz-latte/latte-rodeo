package at.arz.latte.rodeo.pipeline.restapi;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "steps")
@XmlAccessorType(XmlAccessType.FIELD)
public class StepItems {

	@XmlElement(name = "step")
	private List<StepItem> steps;

	protected StepItems() {
		// tool constructor
	}

	public StepItems(List<StepItem> steps) {
		super();
		this.steps = steps;
	}

	public List<StepItem> getSteps() {
		if (steps == null) {
			return Collections.emptyList();
		}
		return steps;
	};

}
