package at.arz.latte.rodeo.infrastructure;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;


@ApplicationScoped
public class EventDispatcher
		implements StartupListener {
	private static final Logger log=Logger.getLogger(EventDispatcher.class.getName());

	private static EventDispatcher current;

	@Inject
	private BeanManager beanManager;

	public EventDispatcher() {
		this(null);
	}

	public EventDispatcher(BeanManager beanManager) {
		current = this;
		this.beanManager = beanManager;
	}

	public static final void notify(Object event) {
		current.beanManager.fireEvent(event);
	}

	@Override
	public void onStartup() {
		log.info("event dispatcher started");
	}
}
