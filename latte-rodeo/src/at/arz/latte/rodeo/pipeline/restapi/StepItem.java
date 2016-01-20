package at.arz.latte.rodeo.pipeline.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import at.arz.latte.rodeo.pipeline.StepName;

@XmlAccessorType(XmlAccessType.FIELD)
public class StepItem {

	private StepName name;

	protected StepItem() {
		// tool constructor
	}

	public StepItem(StepName name) {
		this.name = name;
	}

	public StepName getName() {
		return name;
	}
}
