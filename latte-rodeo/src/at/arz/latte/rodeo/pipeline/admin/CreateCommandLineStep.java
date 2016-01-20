package at.arz.latte.rodeo.pipeline.admin;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.CommandLineStep;
import at.arz.latte.rodeo.pipeline.CommandLineStepConfiguration;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepName;

import com.sun.istack.NotNull;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateCommandLineStep
		implements RodeoCommand<Long> {

	@XmlTransient
	@Inject
	private RodeoModel model;

	@NotNull
	private StepName name;

	@NotNull
	private CommandLineStepConfiguration configuration;

	protected CreateCommandLineStep() {
		// tool constructor
	}

	public CreateCommandLineStep(StepName name, CommandLineStepConfiguration configuration) {
		this.name = name;
		this.configuration = configuration;
	}

	@Override
	public Long execute() {
		Step step = new CommandLineStep(name, configuration);
		model.create(step);
		return step.getId();
	}

	public CommandLineStepConfiguration getConfiguration() {
		return configuration;
	}

	public StepName getName() {
		return name;
	}

}