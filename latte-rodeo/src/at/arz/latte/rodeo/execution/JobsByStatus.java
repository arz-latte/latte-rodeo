package at.arz.latte.rodeo.execution;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import at.arz.latte.rodeo.api.RodeoQuery;
import at.arz.latte.rodeo.execution.Job.Status;

public class JobsByStatus
		implements RodeoQuery<List<Job>> {

	public static List<Status> ALL = Arrays.asList(Job.Status.CREATED, Job.Status.RUNNING, Job.Status.WAITING);
	public static List<Status> RUNNING = Arrays.asList(Job.Status.RUNNING);
	public static List<Status> QUEUED = Arrays.asList(Job.Status.CREATED, Job.Status.WAITING);

	private List<Status> status;

	public JobsByStatus() {
		this.status = ALL;
	}

	public JobsByStatus(List<Status> status) {
		this.status = status;
	}

	@Override
	public List<Job> execute(EntityManager entityManager) {
		TypedQuery<Job> query = createQuery(entityManager);
		return query.getResultList();
	}

	private TypedQuery<Job> createQuery(EntityManager entityManager) {
		TypedQuery<Job> query = entityManager.createQuery(	"select o from Job o where o.status IN :status order by o.created DESC",
															Job.class);
		query.setParameter("status", status);
		return query;
	}

}
