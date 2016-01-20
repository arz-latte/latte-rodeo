package at.arz.latte.rodeo.pipeline.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.rodeo.pipeline.StepConfiguration;
import at.arz.latte.rodeo.pipeline.StepName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StepDetail {

	private StepName stepName;

	private StepConfiguration configuration;

	protected StepDetail() {
		// tool constructor
	}

	public StepDetail(StepName stepName, StepConfiguration configuration) {
		this.stepName = stepName;
		this.configuration = configuration;
	}

	public StepConfiguration getConfiguration() {
		return configuration;
	}

	public StepName getStepName() {
		return stepName;
	}

}
