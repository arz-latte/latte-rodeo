package at.arz.latte.rodeo.project.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateProject {

	private String name;

	CreateProject() {
		// tool constructor
	}

	public CreateProject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


}
