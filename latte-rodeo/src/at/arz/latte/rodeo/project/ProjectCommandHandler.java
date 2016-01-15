package at.arz.latte.rodeo.project;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.project.command.CreateProject;
import at.arz.latte.rodeo.project.exception.ProjectExists;

@Stateless
@LocalBean
public class ProjectCommandHandler {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	public void execute(CreateProject command) {
		String projectName = command.getName();
		List<Project> list = findProjectByName(projectName);
		if (list.isEmpty()) {
			Project project = new Project(projectName);
			entityManager.persist(project);
		} else {
			throw new ProjectExists(projectName);
		}
	}

	private List<Project> findProjectByName(String projectName) {
		TypedQuery<Project> query = entityManager.createNamedQuery(Project.FIND_BY_NAME, Project.class);
		query.setParameter("name", projectName);
		return query.getResultList();
	}

}
