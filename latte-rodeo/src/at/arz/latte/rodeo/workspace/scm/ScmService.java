package at.arz.latte.rodeo.workspace.scm;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import at.arz.latte.rodeo.api.ObjectNotFound;
import at.arz.latte.rodeo.infrastructure.StartupListener;
import at.arz.latte.rodeo.scm.ScmType;
import at.arz.latte.rodeo.workspace.Settings;
import at.arz.latte.rodeo.workspace.Workspace;

@ApplicationScoped
public class ScmService
		implements StartupListener {

	private static final Logger log = Logger.getLogger(ScmService.class.getSimpleName());

	@Inject
	private Workspace workspace;

	private Map<ScmType, ScmProvider> providers;

	public ScmService() {
		providers = new HashMap<ScmType, ScmProvider>();
	}

	public void checkout(ScmType type, String moduleName, String branch) {
		provider(type).checkout();
	}

	private ScmProvider provider(ScmType type) {
		ScmProvider provider = providers.get(type);
		if (provider == null) {
			throw new ObjectNotFound(ScmType.class, type);
		}
		return provider;
	}

	@Override
	public void onStartup() {
		Settings settings = workspace.getSettings("scm");
		String[] providerNames = settings.propertyAsArray("provider");
		for (String provider : providerNames) {
			buildScmProvider(provider);
		}
		log.info("scm providers loaded. known providers:" + providers.keySet());
	}

	private void buildScmProvider(String provider) {
		Settings settings = workspace.getSettings("scm." + provider);
		String type = settings.property("type");
		if (type == null) {
			log.warning("scm provider '" + provider
						+ "' ignored. required property scm."
						+ provider
						+ ".type not found");
			return;
		}

		providers.put(new ScmType(type), new ScmProvider(workspace, settings));
	}

}
