package at.arz.latte.rodeo.scm.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.scm.ScmLocation;
import at.arz.latte.rodeo.scm.Scm;

public class GetScmRepositories
		implements RodeoQuery<GetScmRepositoriesResult> {

	@Override
	public GetScmRepositoriesResult execute(EntityManager entityManager) {
		TypedQuery<Scm> query = entityManager.createNamedQuery(Scm.FIND_ALL, Scm.class);
		List<Scm> list = query.getResultList();
		ArrayList<ScmLocation> locations = new ArrayList<ScmLocation>();
		for (Scm scmRepository : list) {
			locations.add(scmRepository.getLocation());
		}
		return new GetScmRepositoriesResult(locations);
	}

}
