package at.arz.latte.rodeo.project.admin;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ProjectExists
		extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String name;

	public ProjectExists(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
