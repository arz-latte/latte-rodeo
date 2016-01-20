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
		query.setParameter("location", location);
		List<Scm> list = query.getResultList();
		return list;
	}

	public List<Scm> findByLocationOrName(ScmLocation location, ScmName name) {
		TypedQuery<Scm> query = entityManager.createNamedQuery(Scm.FIND_BY_LOCATION_OR_NAME, Scm.class);
		query.setParameter("location", location);
		query.setParameter("name", name);
		List<Scm> list = query.getResultList();
		return list;
	}

}
