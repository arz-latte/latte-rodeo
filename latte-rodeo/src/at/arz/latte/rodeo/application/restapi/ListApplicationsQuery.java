package at.arz.latte.rodeo.application.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.domain.Application;

public class ListApplicationsQuery implements RodeoQuery<ListApplications> {
	private static final int DEFAULT_FETCH_SIZE = 100;
	private int maxResultSize = DEFAULT_FETCH_SIZE;

	@Override
	public ListApplications execute(EntityManager entityManager) {
		TypedQuery<Application> query = createQuery(entityManager);
		query.setMaxResults(maxResultSize);
		return new ListApplications(map(query.getResultList()));
	}

	List<String> map(List<Application> list) {
		List<String> resultList = new ArrayList<>(list.size());
		for (Application application : list) {
			resultList.add(map(application));
		}
		return resultList;
	}

	String map(Application application) {
		return application.getApplicationName();
	}

	TypedQuery<Application> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(Application.SELECT_ALL, Application.class);
	}

}
