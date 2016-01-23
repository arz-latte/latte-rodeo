package at.arz.latte.rodeo.pipeline;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class PipelineConfiguration {

	@Size(max = Template.MAX_LENGTH)
	private String initScript;

	@Size(max = Template.MAX_LENGTH)
	private String updateScript;

	@Size(max = Template.MAX_LENGTH)
	private String cleanScript;

	@Size(max = Template.MAX_LENGTH)
	private String archiveScript;

	@Size(max = Template.MAX_LENGTH)
	private String removeScript;

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

	public String getUpdateScript() {
		return updateScript;
	}

	public void setUpdateScript(String updateScript) {
		this.updateScript = updateScript;
	}

	public String getCleanScript() {
		return cleanScript;
	}

	public void setCleanScript(String cleanScript) {
		this.cleanScript = cleanScript;
	}

	public String getArchiveScript() {
		return archiveScript;
	}

	public void setArchiveScript(String archiveScript) {
		this.archiveScript = archiveScript;
	}

	public String getRemoveScript() {
		return removeScript;
	}

	public void setRemoveScript(String removeScript) {
		this.removeScript = removeScript;
	}

}
