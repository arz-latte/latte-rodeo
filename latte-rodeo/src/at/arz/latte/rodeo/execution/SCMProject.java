package at.arz.latte.rodeo.execution;

public class SCMProject {

	private String name;
	private String scmRepository;
	private String branch;

	public SCMProject(String name, String scmRepository, String branch) {
		this.name = name;
		this.scmRepository = scmRepository;
		this.branch = branch;
	}

	public String getName() {
		return name;
	}

	public String getBranch() {
		return branch;
	}

	public String getScmRepository() {
		return scmRepository;
	}

	public String getScmModuleName() {
		return name;
	}

}
