package at.arz.latte.rodeo.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Workspace {

	private static final Logger log = Logger.getLogger(Workspace.class.getSimpleName());

	private Settings settings;

	private File homeDir;

	private File jobDir;

	public Settings getSettings(String domain) {
		return settings.settingsFor(domain);
	}

	void initWorkspaceDir() {
		File workspaceDir = settings.getWorkspaceDir();
		if (!workspaceDir.exists()) {
			workspaceDir.mkdirs();
		}
		log.info("rodeo workspace:" + workspaceDir.getAbsolutePath());
	}

	private void initSettings() {
		Properties properties = loadConfiguration();
		settings = new Settings(properties, createResolver(properties));
	}

	private VariableResolver createResolver(Properties properties) {
		Properties resolverProperties = new Properties(properties);
		resolverProperties.setProperty("rodeo.home", homeDir.getAbsolutePath());
		return new VariableResolver(resolverProperties);
	}

	void initHomeDir() {
		VariableResolver resolver = new VariableResolver(System.getProperties());
		homeDir = new File(resolver.resolve(System.getProperty("rodeo.home", "${user.home}/rodeo")));
		if (!homeDir.exists()) {
			homeDir.mkdirs();
		}
		log.info("rodeo home:" + homeDir.getAbsolutePath());
	}

	void initjobDir() {
		jobDir = new File(homeDir, "jobs");
		if (!jobDir.exists()) {
			jobDir.mkdirs();
		}
		log.info("rodeo job idr:" + jobDir.getAbsolutePath());
	}

	public File getJobDir() {
		return jobDir;
	}

	private Properties loadConfiguration() {
		Properties defaultProperties = Settings.loadDefaultProperties();
		File customConfig = new File(homeDir, "etc/rodeo.properties");
		if (customConfig.exists()) {
			try (InputStream is = new FileInputStream(customConfig)) {
				defaultProperties.putAll(Settings.loadProperties(is));
				return defaultProperties;
			} catch (IOException e) {
				log.severe("can't load custom rodeo properties from " + customConfig.getAbsolutePath());
			}
		}
		return defaultProperties;
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

	public void startup() {
		initHomeDir();
		initSettings();
		initWorkspaceDir();
	}

	public File getWorkspaceDir() {
		return settings.getWorkspaceDir();
	}

}
