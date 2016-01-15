package at.arz.latte.rodeo.scm.admin;

import javax.ejb.ApplicationException;

import at.arz.latte.rodeo.scm.ScmLocation;

@ApplicationException(rollback = true)
public class ScmLocationExists
		extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ScmLocation location;

	public ScmLocationExists(ScmLocation location) {
		super(location.toString());
		this.location = location;
	}

	public ScmLocation getLocation() {
		return location;
	}
}
