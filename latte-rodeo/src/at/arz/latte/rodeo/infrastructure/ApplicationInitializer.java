package at.arz.latte.rodeo.infrastructure;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import at.arz.latte.rodeo.workspace.Workspace;

@Singleton
@Startup
public class ApplicationInitializer {
	private static final Logger log=Logger.getLogger(ApplicationInitializer.class.getSimpleName());

	@Inject
	private Instance<StartupListener> startupListeners;

	@Inject
	private Workspace workspace;

	@PostConstruct
	void startup() {
		log.info("initializing rodeo services.");
		workspace.startup();
		notifyStartupListener();
		log.info("rodeo services initialized.");
	}

	private void notifyStartupListener() {
		for (StartupListener startupListener : startupListeners) {
			startupListener.onStartup();
		}
	}

}
