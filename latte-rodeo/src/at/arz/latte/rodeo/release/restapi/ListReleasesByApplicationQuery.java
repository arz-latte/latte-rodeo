package at.arz.latte.rodeo.release.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.release.Application;
import at.arz.latte.rodeo.release.ApplicationRelease;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;

public class ListReleasesByApplicationQuery
		implements RodeoQuery<ListReleases> {

	private static final int DEFAULT_FETCH_SIZE = 100;
	private int maxResultSize = DEFAULT_FETCH_SIZE;
	private Application application;

	@Override
	public ListReleases execute(EntityManager entityManager) {
		TypedQuery<ApplicationRelease> query = createQuery(entityManager);
		query.setMaxResults(maxResultSize);
		query.setParameter("application", application);
		return new ListReleases(map(query.getResultList()));
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	List<ReleaseName> map(List<ApplicationRelease> list) {
		List<ReleaseName> resultList = new ArrayList<>(list.size());
		for (ApplicationRelease release : list) {
			resultList.add(map(release.getRelease()));
		}
		return resultList;
	}

	ReleaseName map(Release release) {
		return release.getReleaseName();
	}

	TypedQuery<ApplicationRelease> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(ApplicationRelease.SELECT_ALL_BY_APPLICATION, ApplicationRelease.class);
	}
}
