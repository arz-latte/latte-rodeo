package at.arz.latte.rodeo.pipeline;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

@Entity
@Table(name = "PIPELINES")
@NamedQueries({	@NamedQuery(name = Pipeline.FIND_ALL, query = "select o from Pipeline o order by o.name asc"),
				@NamedQuery(name = Pipeline.FIND_BY_NAME,
							query = "select o from Pipeline o where o.name like :name order by o.name asc") })

public class Pipeline
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected static final String FIND_ALL = "Pipeline.findAll";
	protected static final String FIND_BY_NAME = "Pipeline.findByName";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "PIPELINE_NAME", unique = true, nullable = false)
	private PipelineName name;

	@OneToMany(mappedBy = "pipeline", cascade=CascadeType.ALL, orphanRemoval = true)
	@OrderColumn(name = "STEP_ORDER")
	private List<PipelineStep> steps;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	private Template archiveScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	private Template cleanScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	private Template initScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	private Template removeScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	private Template updateScript;

	protected Pipeline() {
		// jpa constructor
	}

	public Pipeline(PipelineName name, List<PipelineStep> steps, PipelineConfiguration configuration) {
		this.name = name;
		this.steps = steps;
		for (PipelineStep pipelineStep : steps) {
			pipelineStep.setPipeline(this);
		}
		this.archiveScript = new Template(new TemplateName(name + ".archive"), configuration.getArchiveScript());
		this.cleanScript = new Template(new TemplateName(name + ".clean"), configuration.getCleanScript());
		this.initScript = new Template(new TemplateName(name + ".init"), configuration.getInitScript());
		this.removeScript = new Template(new TemplateName(name + ".remove"), configuration.getRemoveScript());
		this.updateScript = new Template(new TemplateName(name + ".update"), configuration.getUpdateScript());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pipeline other = (Pipeline) obj;
		return Objects.equals(name, other.name);
	}

	public Template getArchiveScript() {
		return archiveScript;
	}

	public Template getCleanScript() {
		return cleanScript;
	}

	public Long getId() {
		return id;
	}

	public Template getInitScript() {
		return initScript;
	}

	public PipelineName getName() {
		return name;
	}

	public Template getRemoveScript() {
		return removeScript;
	}

	public List<PipelineStep> getPipelineSteps() {
		return Collections.unmodifiableList(steps);
	}

	public Template getUpdateScript() {
		return updateScript;
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

}
