package at.arz.latte.rodeo.pipeline.admin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.pipeline.Step;
import at.arz.latte.rodeo.pipeline.StepName;

public class FindStep<T extends Step>
		implements RodeoQuery<List<T>> {

	private StepName name;
	private Class<T> stepType;

	public static final <Q extends Step> FindStep<Q> forName(StepName name, Class<Q> stepType) {
		return new FindStep<Q>(name, stepType);
	}
	
	public FindStep(StepName name, Class<T> stepType) {
		this.name = name;
		this.stepType = stepType;
	}

	@Override
	public List<T> execute(EntityManager entityManager) {
		TypedQuery<T> query = entityManager.createQuery("select o from " + stepType.getSimpleName()
														+ " o where o.name=:name", stepType);
		query.setParameter("name", name);
		return query.getResultList();
	}

}
