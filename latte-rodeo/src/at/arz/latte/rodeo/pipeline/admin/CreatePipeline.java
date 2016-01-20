package at.arz.latte.rodeo.pipeline.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.AmbiguousQuery;
import at.arz.latte.rodeo.api.ObjectNotFound;
import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.infrastructure.RodeoModel;
import at.arz.latte.rodeo.pipeline.FindSteps;
import at.arz.latte.rodeo.pipeline.Pipeline;
import at.arz.latte.rodeo.pipeline.PipelineConfiguration;
import at.arz.latte.rodeo.pipeline.PipelineName;
import at.arz.latte.rodeo.pipeline.PipelineStep;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.restapi.PipelineStepItem;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatePipeline
		implements RodeoCommand<Long> {

	@XmlTransient
	@Inject
	private RodeoModel model;

	@NotNull
	private PipelineName name;

	@XmlElement(name = "stepItems")
	@NotNull
	private List<PipelineStepItem> pipelineStepItems;

	@NotNull
	private PipelineConfiguration configuration;

	protected CreatePipeline() {
		// tool constructor
	}

	public CreatePipeline(PipelineName name, List<PipelineStepItem> pipelineStepItems, PipelineConfiguration configuration) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(pipelineStepItems, "pipelineStepItems required");
		Objects.requireNonNull(configuration, "configuration required");
		this.name = name;
		this.pipelineStepItems=pipelineStepItems;
		this.configuration = configuration;
	}

	@Override
	public Long execute() {
		List<PipelineStep> pipelineSteps = createPiplineSteps();
		Pipeline pipeline = new Pipeline(name, pipelineSteps, configuration);
		model.create(pipeline);
		return pipeline.getId();
	}

	private List<PipelineStep> createPiplineSteps() {
		if(pipelineStepItems==null||pipelineStepItems.isEmpty()){
			return Collections.emptyList();
		}
		List<PipelineStep> pipelineSteps = new ArrayList<>();

		for (PipelineStepItem item : pipelineStepItems) {
			List<Step> result = model.query(new FindSteps(item.getName()));
			if (result.isEmpty()) {
				throw new ObjectNotFound(Step.class, item.getName());
			}
			if(result.size() > 1){
				throw new AmbiguousQuery(); 
			}
			PipelineStep newStep = new PipelineStep(result.get(0));
			newStep.setOptional(item.isOptional());
			pipelineSteps.add(newStep);
		}
		return pipelineSteps;
	}

}
