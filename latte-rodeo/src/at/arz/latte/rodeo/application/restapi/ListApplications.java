package at.arz.latte.rodeo.application.restapi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "applications")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListApplications implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "application")
	private List<String> names;

	ListApplications() {
		// tool constructor
	}

	public ListApplications(List<String> names) {
		this.names = names;
	}

}
