package at.arz.latte.rodeo.release.restapi;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.release.Release;
import at.arz.latte.rodeo.release.ReleaseName;

public class FindReleaseByNameQuery
		implements RodeoQuery<Release> {

	private ReleaseName releaseName;

	@Override
	public Release execute(EntityManager entityManager) {
		TypedQuery<Release> createQuery = createQuery(entityManager);
		createQuery.setParameter("releaseName", releaseName);
		return createQuery.getSingleResult();
	}

	public void setReleaseName(ReleaseName releaseName) {
		this.releaseName = releaseName;
	}

	TypedQuery<Release> createQuery(EntityManager entityManager) {
		return entityManager.createNamedQuery(Release.SELECT_BY_NAME, Release.class);
	}

}
