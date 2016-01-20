package at.arz.latte.rodeo.scm.restapi;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "repositories")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetScmRepositoriesResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "scm")
	private List<ScmResult> scms;

	GetScmRepositoriesResult() {
		// tool constructor
	}

	public GetScmRepositoriesResult(List<ScmResult> scms) {
		this.scms = scms;
	}

	public List<ScmResult> getScms() {
		return Collections.unmodifiableList(scms);
	}

}
