package at.arz.latte.rodeo.release.restapi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "releases")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListReleases implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "release")
	private List<String> names;

	ListReleases() {
		// tool constructor
	}

	public ListReleases(List<String> names) {
		this.names = names;
	}
}
