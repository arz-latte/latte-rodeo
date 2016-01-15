package at.arz.latte.rodeo.scm;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Session Bean implementation class ScmAdmin
 */
@Stateless
@LocalBean
public class ScmRepository {

	@PersistenceContext(unitName = "latte-rodeo")
	private EntityManager entityManager;

	public void create(Scm scm) {
		entityManager.persist(scm);
	}

	public List<Scm> findByLocation(ScmLocation location) {
		TypedQuery<Scm> query = entityManager.createNamedQuery(Scm.FIND_BY_LOCATION, Scm.class);
		query.setParameter("location", location.toString());
		List<Scm> list = query.getResultList();
		return list;
	}

}
