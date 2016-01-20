package at.arz.latte.rodeo.pipeline;

import java.io.Serializable;

public class PipelineStepPrimaryKey
		implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long pipeline;
	private Long step;

	public PipelineStepPrimaryKey() {
		// jpa constructor
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pipeline == null) ? 0 : pipeline.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PipelineStepPrimaryKey other = (PipelineStepPrimaryKey) obj;
		if (pipeline == null) {
			if (other.pipeline != null)
				return false;
		} else if (!pipeline.equals(other.pipeline))
			return false;
		if (step == null) {
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		return true;
	}

}
