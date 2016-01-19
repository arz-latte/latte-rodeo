package at.arz.latte.rodeo.execution;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;

public class FindJobs
		implements RodeoQuery<List<Job>> {

	private JobIdentifier identifier;
	private Job.Status status;

	public FindJobs(JobIdentifier identifier, Job.Status status) {
		this.identifier = identifier;
		this.status = status;
	}

	public FindJobs(JobIdentifier identifier) {
		this(identifier, null);
	}

	@Override
	public List<Job> execute(EntityManager entityManager) {
		TypedQuery<Job> query = createQuery(entityManager);
		return query.getResultList();
	}

	private TypedQuery<Job> createQuery(EntityManager entityManager) {
		if (status == null && identifier == null) {
			return entityManager.createQuery("select o from Job o", Job.class);
		}

		if (status == null) {
			TypedQuery<Job> query = entityManager.createQuery(	"select o from Job o where o.identifier like :identifier",
																Job.class);
			query.setParameter("identifier", identifier);
			return query;
		}

		TypedQuery<Job> query = entityManager.createQuery(	"select o from Job o where o.identifier like :identifier and status=:status",
															Job.class);
		query.setParameter("identifier", identifier);
		query.setParameter("status", status);
		return query;
	}

}
