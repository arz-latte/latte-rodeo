package at.arz.latte.rodeo.workspace.scm;


public interface ScmProvider {

	void checkout(String moduleName, String branch);

}
