package at.arz.latte.rodeo.pipeline;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

/**
 * Entity implementation class for Entity: PipelineStep
 *
 */
@Entity
@Table(name = "PIPELINE_STEPS")
@IdClass(PipelineStepPrimaryKey.class)
public class PipelineStep
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	private Pipeline pipeline;

	@Id
	@OneToOne
	private Step step;

	private boolean optional;

	protected PipelineStep() {
		// jpa constructor
	}

	void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public PipelineStep(Step step) {
		this.step = step;
	}
	
	public StepName getStepName(){
		return step.getName();
	}

	public Step getStep() {
		return step;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
