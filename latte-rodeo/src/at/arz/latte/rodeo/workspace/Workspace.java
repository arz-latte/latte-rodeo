package at.arz.latte.rodeo.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import at.arz.latte.rodeo.infrastructure.StartupListener;

@Singleton
@Startup
public class Workspace {

	private static final Logger log = Logger.getLogger(Workspace.class.getSimpleName());

	@Inject
	private Instance<StartupListener> startupListeners;

	private WorkspaceSettings settings;

	public Properties getSettings(String domain) {
		return settings.propertiesFor(domain);
	}

	void initWorkspaceDir() {
		File workspaceDir = settings.getWorkspaceDir();
		if (!workspaceDir.exists()) {
			workspaceDir.mkdirs();
		}
		log.info("rodeo workspace:" + workspaceDir.getAbsolutePath());
	}

	private void initSettings() {
		settings = new WorkspaceSettings(loadConfiguration());
	}

	void initHomeDir() {
		File homeDir = WorkspaceSettings.getHomeDir();
		if (!homeDir.exists()) {
			homeDir.mkdirs();
		}
		log.info("rodeo home:" + homeDir.getAbsolutePath());

	}

	private Properties loadConfiguration() {
		Properties defaultProperties = WorkspaceSettings.loadDefaultProperties();
		File customConfig = new File(WorkspaceSettings.getHomeDir(), "etc/rodeo.properties");
		if (customConfig.exists()) {
			try (InputStream is = new FileInputStream(customConfig)) {
				defaultProperties.putAll(WorkspaceSettings.loadProperties(is));
				return defaultProperties;
			} catch (IOException e) {
				log.severe("can't load custom rodeo properties from " + customConfig.getAbsolutePath());
			}
		}
		return defaultProperties;
	}

	private void notifyStartupListener() {
		for (StartupListener startupListener : startupListeners) {
			startupListener.onStartup();
		}
	}

	@PreDestroy
	void shutdown() {
		log.info("shutting down rodeo...");
		waitUntilActiveCommandsCompleted();
		log.info("rodeo stopped.");
	}

	private void waitUntilActiveCommandsCompleted() {
		// TODO
	}

	@PostConstruct
	void startup() {
		log.info("initializing rodeo services.");
		initSettings();
		initHomeDir();
		initWorkspaceDir();
		notifyStartupListener();
		log.info("rodeo services initialized.");
	}

}
