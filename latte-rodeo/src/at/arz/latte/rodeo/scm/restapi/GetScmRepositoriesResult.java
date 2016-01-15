package at.arz.latte.rodeo.scm.restapi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.rodeo.scm.ScmLocation;

@XmlRootElement(name = "repositories")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetScmRepositoriesResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "repository")
	private List<ScmLocation> locations;

	GetScmRepositoriesResult() {
		// tool constructor
	}

	public GetScmRepositoriesResult(List<ScmLocation> locations) {
		this.locations = locations;
	}

}
