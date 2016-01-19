package at.arz.latte.rodeo.pipeline.admin;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepName;

public class FindStep<T>
		implements RodeoQuery<T> {

	private StepName name;
	private Class<T> stepType;

	public static final <Q extends Step> FindStep<Q> forName(StepName name, Class<Q> stepType) {
		return null;
	}
	
	public FindStep(StepName name, Class<T> stepType) {
		this.name = name;
		this.stepType = stepType;
	}

	@Override
	public T execute(EntityManager entityManager) {
		TypedQuery<T> query = entityManager.createQuery("select o from " + stepType.getSimpleName()
														+ " + o where o.name=:name", stepType);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

}
