package at.arz.latte.rodeo.execution;

public class Project {

	private String name;
	private String scmRepository;
	private String branch;

	public Project(String name, String scmRepository, String branch) {
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
