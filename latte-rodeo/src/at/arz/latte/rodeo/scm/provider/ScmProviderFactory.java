package at.arz.latte.rodeo.scm.provider;

import at.arz.latte.rodeo.scm.CvsScmRepository;
import at.arz.latte.rodeo.workspace.Workspace;

public class ScmProviderFactory {

	private Workspace workspace;

	public ScmProviderFactory(Workspace workspace) {
		this.workspace = workspace;
	}

	public ScmProvider getRepository(String repositoryURL) {
		if (repositoryURL.startsWith(":pserver:")) {
			return new CvsScmRepository(workspace, repositoryURL);
		}
		throw new IllegalStateException("unknown repository url:" + repositoryURL);
	}
}
