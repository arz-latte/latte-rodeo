package at.arz.latte.rodeo.pipeline;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlElement("configuration")
public class CommandLineStepConfiguration extends 
StepConfiguration {

	@Size(max = Template.MAX_LENGTH)
	private String mainScript;

	@Size(max = Template.MAX_LENGTH)
	private String successScript;

	@Size(max = Template.MAX_LENGTH)
	private String failureScript;

	public String getMainScript() {
		return mainScript;
	}

	public void setMainScript(String mainScript) {
		this.mainScript = mainScript;
	}

	public String getSuccessScript() {
		return successScript;
	}

	public void setSuccessScript(String successScript) {
		this.successScript = successScript;
	}

	public String getFailureScript() {
		return failureScript;
	}

	public void setFailureScript(String failureScript) {
		this.failureScript = failureScript;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
