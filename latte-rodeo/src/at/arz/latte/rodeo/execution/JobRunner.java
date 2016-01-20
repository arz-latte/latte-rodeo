package at.arz.latte.rodeo.execution;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.arz.latte.rodeo.execution.Job.Status;
import at.arz.latte.rodeo.infrastructure.EventDispatcher;
import at.arz.latte.rodeo.workspace.AsynchronousRunner;

class JobRunner
		implements Runnable {

	private static final Logger logger = Logger.getLogger(JobRunner.class.getName());

	private String[] environmentVariables;
	private File workDirectory;
	private JobIdentifier identifier;
	private File logFile;
	private String commandLine;

	private AsynchronousRunner runner;

	private JobLog log;

	public JobRunner(AsynchronousRunner runner, JobIdentifier identifier) {
		this.runner = runner;
		this.identifier = identifier;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}

	public void setEnvironmentVariables(String[] environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public void setWorkDirectory(File workDirectory) {
		this.workDirectory = workDirectory;
	}

	public void run() {
		EventDispatcher.notify(new JobStatusChanged(identifier, Status.WAITING));
		if (commandLine == null || commandLine.length() == 0) {
			throw new RuntimeException("invalid emtpy command line");
		}
		int rc = -1;
		try {
			rc = executeProcess(commandLine);
			if (rc == 0) {
				logger.info("job " + identifier + " finished successfully.");
				JobStatusChanged event = new JobStatusChanged(identifier, Status.SUCCESS);
				runner.eventFromAsynchronousThread(event);
			}
		} finally {
			if (rc != 0) {
				logger.info("job " + identifier + " failed with returncode:" + rc);
				JobStatusChanged event = new JobStatusChanged(identifier, Status.FAILED);
				runner.eventFromAsynchronousThread(event);
			}
		}
	}

	protected Process createProcess(String command) {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Trying to create Process '" + command + "' with workingDir='" + workDirectory + "'...");
		}
		try {
			return Runtime.getRuntime().exec(command, environmentVariables, workDirectory);
		} catch (Throwable t) {
			throw new RuntimeException("can't create process '" + command + "' in '" + workDirectory + "'", t);
		}
	}

	protected int executeProcess(String commandLine) {
		logger.info("execute batch process: " + commandLine + ", " + workDirectory);
		Process p = createProcess(commandLine);
		runner.eventFromAsynchronousThread(new JobStatusChanged(identifier, Job.Status.RUNNING));
		log = new JobLog(logFile);
		log.addLogStream(p.getInputStream(), "STDOUT");
		log.addLogStream(p.getErrorStream(), "STDERR");
		try {
			try {
				p.waitFor();
			} catch (InterruptedException ie) {
				logger.severe("execution interrupted:" + ie.getMessage());
			}
			return p.exitValue();
		} catch (Throwable t) {
			throw new RuntimeException("execution failed:" + t.getMessage(), t);
		} finally {
			log.waitForCompletion();
		}
	}

}
