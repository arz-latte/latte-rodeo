package at.arz.latte.rodeo.project.admin;

import at.arz.latte.rodeo.journal.JournalEvent;
import at.arz.latte.rodeo.project.ProjectName;

public class ProjectCreated
		implements JournalEvent {

	private ProjectName name;

	public ProjectCreated(ProjectName name) {
		this.name = name;
	}

	public ProjectName getName() {
		return name;
	}
}
