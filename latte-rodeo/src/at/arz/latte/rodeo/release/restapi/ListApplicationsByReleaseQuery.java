package at.arz.latte.rodeo.release.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationName;
import at.arz.latte.rodeo.release.ApplicationRelease;
import at.arz.latte.rodeo.release.Release;

public class ListApplicationsByReleaseQuery
		implements RodeoQuery<ListApplications> {

	private static final int DEFAULT_FETCH_SIZE = 100;
	private int maxResultSize = DEFAULT_FETCH_SIZE;
	private Release release;

	@Override
	public ListApplications execute(EntityManager entityManager) {
		TypedQuery<ApplicationRelease> query = createQuery(entityManager);
		query.setMaxResults(maxResultSize);
		query.setParameter("release", release);
		return new ListApplications(map(query.getResultList()));
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	List<ApplicationName> map(List<ApplicationRelease> list) {
		List<ApplicationName> resultList = new ArrayList<>(list.size());
		for (ApplicationRelease application : list) {
			resultList.add(map(application.getApplication()));
		}
		return resultList;
	}

	ApplicationName map(Application application) {
		return application.getName();
	}

	TypedQuery<ApplicationRelease> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(Application.SELECT_ALL, ApplicationRelease.class);
	}

}
