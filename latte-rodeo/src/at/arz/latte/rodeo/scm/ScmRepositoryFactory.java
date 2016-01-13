package at.arz.latte.rodeo.scm;

import at.arz.latte.rodeo.workspace.Workspace;

public class ScmRepositoryFactory {

	private Workspace workspace;

	public ScmRepositoryFactory(Workspace workspace) {
		this.workspace = workspace;
	}

	public ScmRepository getRepository(String repositoryURL) {
		if (repositoryURL.startsWith(":pserver:")) {
			return new CvsScmRepository(workspace, repositoryURL);
		}
		throw new IllegalStateException("unknown repository url:" + repositoryURL);
	}
}
