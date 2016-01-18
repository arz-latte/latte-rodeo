package at.arz.latte.rodeo.release.restapi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import at.arz.latte.rodeo.release.ApplicationName;

@XmlRootElement(name = "applications")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListApplications implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "application")
	private List<ApplicationName> names;

	ListApplications() {
		// tool constructor
	}

	public ListApplications(List<ApplicationName> names) {
		this.names = names;
	}

}
