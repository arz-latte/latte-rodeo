package at.arz.latte.rodeo.infrastructure;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.rodeo.api.RodeoCommand;
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

	public <R> R query(RodeoQuery<R> query) {
		return query.execute(entityManager);
	}

	public <R> R execute(RodeoCommand<R> command) {
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

}
