package at.arz.latte.rodeo.scm.admin;

import at.arz.latte.rodeo.journal.JournalEvent;
import at.arz.latte.rodeo.scm.ScmLocation;


public class ScmCreated
		implements JournalEvent {

	private ScmLocation location;

	public ScmCreated(ScmLocation location) {
		super();
		this.location = location;
	}

	public ScmLocation getLocation() {
		return location;
	}

}
