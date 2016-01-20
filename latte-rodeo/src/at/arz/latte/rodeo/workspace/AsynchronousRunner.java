package at.arz.latte.rodeo.workspace;

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
public class AsynchronousRunner {

	private ScheduledThreadPoolExecutor poolExecutor;

	@Inject
	private BeanManager beanManager;

	@PostConstruct
	public void setup() {
		poolExecutor = new ScheduledThreadPoolExecutor(10);
	}

	public void runAsynchron(Runnable runnable) {
		poolExecutor.execute(runnable);
	}

	@PreDestroy
	void shutdown() {
		poolExecutor.shutdown();
	}

	public void eventFromAsynchronousThread(Object event) {
		beanManager.fireEvent(event);
	}

}
