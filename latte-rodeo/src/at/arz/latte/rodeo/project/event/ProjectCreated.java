package at.arz.latte.rodeo.project.event;

import at.arz.latte.rodeo.journal.JournalEvent;

public class ProjectCreated
		implements JournalEvent {

	private String name;

	public ProjectCreated(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
