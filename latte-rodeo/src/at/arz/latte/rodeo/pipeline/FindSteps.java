package at.arz.latte.rodeo.pipeline;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;

public class FindSteps
		implements RodeoQuery<List<Step>> {

	private StepName name;

	public FindSteps() {
		// finds all
	}

	public FindSteps(StepName name) {
		this.name = name;
	}

	@Override
	public List<Step> execute(EntityManager entityManager) {
		return createQuery(entityManager).getResultList();
	}

	private TypedQuery<Step> createQuery(EntityManager entityManager) {
		if (name == null) {
			return entityManager.createNamedQuery(Step.FIND_ALL, Step.class);
		}

		TypedQuery<Step> query = entityManager.createNamedQuery(Step.FIND_BY_NAME, Step.class);
		query.setParameter("name", name);
		return query;
	}

}
