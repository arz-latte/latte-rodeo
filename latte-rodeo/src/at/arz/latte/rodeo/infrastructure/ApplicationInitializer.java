package at.arz.latte.rodeo.infrastructure;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;


@Singleton
@Startup
public class ApplicationInitializer {
	private static final Logger log=Logger.getLogger(ApplicationInitializer.class.getSimpleName());

	@Inject
	private Instance<StartupListener> startupListeners;
	
	@PostConstruct
	void startup(){
		log.info("initializing rodeo services.");
		for (StartupListener startupListener : startupListeners) {
			startupListener.onStartup();
		}
		log.info("rodeo services initialized.");
	}
	
	@PreDestroy
	void shutdown(){
		log.info("rodeo stopped.");
	}
}
