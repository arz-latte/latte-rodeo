package at.arz.latte.rodeo.infrastructure;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import at.arz.latte.rodeo.api.RodeoCommand;
import at.arz.latte.rodeo.api.RodeoFunction;
import at.arz.latte.rodeo.api.RodeoQuery;

/**
 * Session Bean implementation class RodeoModel
 */
@Stateless
@LocalBean
public class RodeoModel {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	@Inject
	private BeanManager beanManager;

	public <R> R query(@Valid RodeoQuery<R> query) {
		return query.execute(entityManager);
	}

	public <R, T> R apply(@Valid RodeoQuery<T> query, RodeoFunction<T, R> function) {
		return function.apply(query.execute(entityManager));
	}

	public <R, T> List<R> applyAll(@Valid RodeoQuery<List<T>> query, RodeoFunction<T, R> function) {
		List<T> result = query.execute(entityManager);
		List<R> list = new ArrayList<R>();
		for (T t : result) {
			list.add(function.apply(t));
		}
		return list;
	}

	public <R> R execute(@Valid RodeoCommand<R> command) {
		CreationalContext<Object> context = beanManager.createCreationalContext(null);
		try {
			@SuppressWarnings("unchecked")
			AnnotatedType<Object> type = (AnnotatedType<Object>) beanManager.createAnnotatedType(command.getClass());
			beanManager.createInjectionTarget(type).inject(command, context);
			return command.execute();
		} finally {
			context.release();
		}
	}

	public void create(AbstractEntity entity) {
		entityManager.persist(entity);
	}

	public void remove(AbstractEntity entity) {
		entityManager.remove(entity);
	}

}
