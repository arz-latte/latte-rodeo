package at.arz.latte.rodeo.scm.admin;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ScmNameExists
		extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String name;

	public ScmNameExists(String name) {
		super(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
