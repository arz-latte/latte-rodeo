package at.arz.latte.rodeo.pipeline.admin;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.istack.NotNull;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepConfiguration;
import at.arz.latte.rodeo.pipeline.StepName;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateStep
		implements RodeoCommand<Long> {

	@XmlTransient
	@Inject
	private RodeoModel model;

	@NotNull
	private StepName name;

	@NotNull
	private StepConfiguration configuration;

	protected CreateStep(){
		// tool constructor
	}

	public CreateStep(StepName name, StepConfiguration configuration) {
		this.name = name;
		this.configuration = configuration;
	}

	@Override
	public Long execute() {
		Step step = new Step(name, configuration);
		model.create(step);
		return step.getId();
	}

}
