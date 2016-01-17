package at.arz.latte.rodeo.pipeline;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Externalizer;
import org.apache.openjpa.persistence.Persistent;

import at.arz.latte.rodeo.infrastructure.AbstractEntity;

@Entity
@Table(name = "STEPS")
@NamedQueries({	@NamedQuery(name = Step.FIND_ALL, query = "select o from Step o order by o.name asc"),
				@NamedQuery(name = Step.FIND_BY_NAME,
							query = "select o from Step o where o.name like :name order by o.name asc") })
public class Step
		extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	protected static final String FIND_ALL = "Step.findAll";
	protected static final String FIND_BY_NAME = "Step.findByName";

	@Id
	@Column(name = "OID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQUENCES")
	private Long id;

	@Persistent
	@Externalizer("toString")
	@Column(name = "STEP_NAME", unique = true, nullable = false)
	private StepName name;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "MAIN_SCRIPT")
	private Template mainScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "SUCCESS_SCRIPT")
	private Template successScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "FAILURE_SCRIPT")
	private Template failureScript;

	protected Step() {
		// jpa constructor
	}

	public Step(StepName name, StepConfiguration configuration) {
		Objects.requireNonNull(name, "name required");
		Objects.requireNonNull(name, "configuration required");
		this.name = name;
		mainScript = new Template(new TemplateName(name + ".mainScript"), configuration.getMainScript());
		successScript = new Template(new TemplateName(name + ".successScript"), configuration.getSuccessScript());
		failureScript = new Template(new TemplateName(name + ".failureScript"), configuration.getFailureScript());
	}

	public StepConfiguration getConfiguration() {
		StepConfiguration configuration = new StepConfiguration();
		configuration.setFailureScript(failureScript.getContent());
		configuration.setSuccessScript(successScript.getContent());
		configuration.setMainScript(mainScript.getContent());
		return configuration;
	}

	public Long getId() {
		return id;
	}

	public StepName getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Step other = (Step) obj;
		return Objects.equals(name, other.name);
	}

}
