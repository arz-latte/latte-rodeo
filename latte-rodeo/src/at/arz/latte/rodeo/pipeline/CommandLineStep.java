package at.arz.latte.rodeo.pipeline;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: JavaStep
 *
 */
@Entity
@DiscriminatorValue("COMMANDLINE")
public class CommandLineStep
		extends Step {

	
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "MAIN_SCRIPT")
	private Template mainScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "SUCCESS_SCRIPT")
	private Template successScript;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
	@Column(name = "FAILURE_SCRIPT")
	private Template failureScript;

	protected CommandLineStep() {
		super();
	}

	public CommandLineStep(StepName name, CommandLineStepConfiguration configuration) {
		super(name, configuration);
		Objects.requireNonNull(name, "configuration required");
		mainScript = new Template(new TemplateName(name + ".mainScript"), configuration.getMainScript());
		successScript = new Template(new TemplateName(name + ".successScript"), configuration.getSuccessScript());
		failureScript = new Template(new TemplateName(name + ".failureScript"), configuration.getFailureScript());
	}

	public StepConfiguration getConfiguration() {
		CommandLineStepConfiguration configuration = new CommandLineStepConfiguration();
		configuration.setFailureScript(failureScript.getContent());
		configuration.setSuccessScript(successScript.getContent());
		configuration.setMainScript(mainScript.getContent());
		return configuration;
	}

	public String getMainScript() {
		return mainScript.getContent();
	}
   
}
