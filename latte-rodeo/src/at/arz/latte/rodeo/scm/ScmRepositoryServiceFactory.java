package at.arz.latte.rodeo.scm;

import at.arz.latte.rodeo.workspace.Workspace;

public class ScmRepositoryServiceFactory {

	private Workspace workspace;

	public ScmRepositoryServiceFactory(Workspace workspace) {
		this.workspace = workspace;
	}

	public ScmRepositoryService getRepository(String repositoryURL) {
		if (repositoryURL.startsWith(":pserver:")) {
			return new CvsScmRepository(workspace, repositoryURL);
		}
		throw new IllegalStateException("unknown repository url:" + repositoryURL);
	}
}
