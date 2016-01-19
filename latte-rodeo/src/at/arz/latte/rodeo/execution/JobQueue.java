package at.arz.latte.rodeo.execution;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class JobQueue {

	private ScheduledThreadPoolExecutor poolExecutor;

	@Inject
	private BeanManager beanManager;

	@PostConstruct
	public void setup() {
		poolExecutor = new ScheduledThreadPoolExecutor(10);
	}

	public void submit(Runnable runnable) {
		poolExecutor.execute(runnable);
	}

	@PreDestroy
	public void shutdown() {
		poolExecutor.shutdown();
	}

	public void jobStatusChanged(JobStatusChanged event) {
		beanManager.fireEvent(event);
	}

}

