package at.arz.latte.rodeo.release.restapi;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationName;

public class FindApplicationByNameQuery
		implements RodeoQuery<Application> {

	private ApplicationName applicationName;

	@Override
	public Application execute(EntityManager entityManager) {
		TypedQuery<Application> createQuery = createQuery(entityManager);
		createQuery.setParameter("name", applicationName);
		return createQuery.getSingleResult();
	}

	public void setApplicationName(ApplicationName applicationName) {
		this.applicationName = applicationName;
	}

	TypedQuery<Application> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(Application.SELECT_BY_NAME, Application.class);
	}

}
