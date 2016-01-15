package at.arz.latte.rodeo.domain;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.arz.latte.rodeo.api.RodeoQuery;

/**
 * Session Bean implementation class RodeoModel
 */
@Stateless
@LocalBean
public class RodeoModel {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	public <R> R query(RodeoQuery<R> query) {
		return query.execute(entityManager);
	}

}
