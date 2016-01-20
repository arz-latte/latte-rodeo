package at.arz.latte.rodeo.pipeline;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;

public class FindPipelines
		implements RodeoQuery<List<Pipeline>> {

	private PipelineName name;

	public FindPipelines() {
		// finds all
	}

	public FindPipelines(PipelineName name) {
		this.name = name;
	}

	@Override
	public List<Pipeline> execute(EntityManager entityManager) {
		return createQuery(entityManager).getResultList();
	}

	private TypedQuery<Pipeline> createQuery(EntityManager entityManager) {
		if (name == null) {
			return entityManager.createNamedQuery(Pipeline.FIND_ALL, Pipeline.class);
		}

		TypedQuery<Pipeline> query = entityManager.createNamedQuery(Pipeline.FIND_BY_NAME, Pipeline.class);
		query.setParameter("name", name);
		return query;
	}

}
