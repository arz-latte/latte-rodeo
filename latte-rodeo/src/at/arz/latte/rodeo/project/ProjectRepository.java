package at.arz.latte.rodeo.project;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class ProjectRepository {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	public void create(Project project) {
		entityManager.persist(project);
	}

	public List<Project> findProjectByName(String projectName) {
		TypedQuery<Project> query = entityManager.createNamedQuery(Project.FIND_BY_NAME, Project.class);
		query.setParameter("name", projectName);
		return query.getResultList();
	}

}
