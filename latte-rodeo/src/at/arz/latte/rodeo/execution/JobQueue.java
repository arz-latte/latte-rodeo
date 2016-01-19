package at.arz.latte.rodeo.execution;

import java.util.concurrent.Semaphore;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import at.arz.latte.rodeo.workspace.Settings;
import at.arz.latte.rodeo.workspace.Workspace;

@ApplicationScoped
public class JobQueue {

	@Inject
	private Workspace workspace;

	private Semaphore maxConcurrentJobs;

	public JobQueue() {
	}

	@PostConstruct
	private void setup() {
		Settings settings = workspace.getSettings("workspace");
		initMaxConcurrentJobs(settings);
	}

	private void initMaxConcurrentJobs(Settings settings) {
		int value = Integer.parseInt(settings.property("maxConcurrentJobs", "10"));
		maxConcurrentJobs = new Semaphore(value);
	}

	public void submit(JobProcessor processor) {
		processor.execute();
	}


}

