package at.arz.latte.rodeo.release.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.release.Release;

public class ListReleasesQuery implements RodeoQuery<ListReleases> {
	private static final int DEFAULT_FETCH_SIZE = 100;
	private int maxResultSize = DEFAULT_FETCH_SIZE;

	@Override
	public ListReleases execute(EntityManager entityManager) {
		TypedQuery<Release> query = createQuery(entityManager);
		query.setMaxResults(maxResultSize);
		return new ListReleases(map(query.getResultList()));
	}

	List<String> map(List<Release> list) {
		List<String> resultList = new ArrayList<>(list.size());
		for (Release release : list) {
			resultList.add(map(release));
		}
		return resultList;
	}

	String map(Release release) {
		return release.getReleaseName();
	}

	TypedQuery<Release> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(Release.SELECT_ALL, Release.class);
	}

}
