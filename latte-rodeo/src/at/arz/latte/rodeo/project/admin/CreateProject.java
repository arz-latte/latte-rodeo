package at.arz.latte.rodeo.project.admin;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import at.arz.latte.rodeo.api.ObjectExists;
import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.project.Project;
import at.arz.latte.rodeo.project.ProjectName;
import at.arz.latte.rodeo.project.ProjectRepository;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateProject
		implements RodeoCommand<Long> {

	@NotNull
	private ProjectName name;

	@XmlTransient
	private ProjectRepository repository;

	CreateProject() {
		// tool constructor
	}

	public CreateProject(ProjectName name) {
		this.name = name;
	}

	public ProjectName getName() {
		return name;
	}

	@Override
	public Long execute() {
		List<Project> projects = repository.findProjectByName(name);
		if (projects.isEmpty()) {
			Project project = new Project(name);
			repository.create(project);
			return project.getId();
		} else {
			throw new ObjectExists(Project.class, "name", name);
		}
	}

}
